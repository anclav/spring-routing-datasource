package org.anclav.doubledatasource.config;


import javax.annotation.Nonnull;
import javax.sql.DataSource;

import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestContextConfiguration {

    private static final PostgreSQLContainer MAIN = new PostgreSQLContainer<>()
        .withInitScript("init_main.sql");

    private static final PostgreSQLContainer REPLICA = new PostgreSQLContainer<>()
        .withInitScript("init_replica.sql");

    static {
        MAIN.start();
        REPLICA.start();
    }

    @Bean
    @Primary
    @DependsOn({"mainDataSource", "replicaDatasource"})
    public DataSource dataSource() {

        ImmutableMap<Object, Object> targetDataSources = ImmutableMap.of(
            RoutingDatasource.Route.PRIMARY, mainDataSource(),
            RoutingDatasource.Route.REPLICA, replicaDatasource()
        );

        RoutingDatasource routingDatasource = new RoutingDatasource();
        routingDatasource.setDefaultTargetDataSource(targetDataSources.get(RoutingDatasource.Route.PRIMARY));
        routingDatasource.setTargetDataSources(targetDataSources);
        return routingDatasource;
    }

    @Bean
    DataSource mainDataSource() {
        return createDataSource(MAIN);
    }

    @Bean
    DataSource replicaDatasource() {
        return createDataSource(REPLICA);
    }

    @Bean
    public BeanPostProcessor dataSourceTraceBeanPostProcessor() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(@Nonnull Object bean, String beanName) throws BeansException {
                if (bean instanceof HikariDataSource) {
                    System.out.println("Wrapped bean " + beanName);
                }
                return bean;
            }
        };
    }

    private static HikariDataSource createDataSource(PostgreSQLContainer container) {
        HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(10);
        ds.setJdbcUrl(container.getJdbcUrl());
        ds.addDataSourceProperty("user", container.getUsername());
        ds.addDataSourceProperty("password", container.getPassword());
        return ds;
    }

}
