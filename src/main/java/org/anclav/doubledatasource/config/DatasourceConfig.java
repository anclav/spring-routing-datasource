package org.anclav.doubledatasource.config;

import javax.sql.DataSource;

import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"local", "testing", "production"})
public class DatasourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "main.datasource")
    protected DataSource mainDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setPoolName("main-pg-pool");
        return hikariDataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "replica.datasource")
    protected DataSource replicaDatasource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setPoolName("replica-pg-pool");
        return hikariDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource(DataSource mainDataSource, DataSource replicaDatasource) {
        ImmutableMap<Object, Object> targetDataSources = ImmutableMap.of(
            RoutingDatasource.Route.PRIMARY, mainDataSource,
            RoutingDatasource.Route.REPLICA, replicaDatasource
        );

        RoutingDatasource routingDatasource = new RoutingDatasource();
        routingDatasource.setDefaultTargetDataSource(mainDataSource);
        routingDatasource.setTargetDataSources(targetDataSources);
        return routingDatasource;
    }
}
