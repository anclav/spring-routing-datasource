package org.anclav.doubledatasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDatasource extends AbstractRoutingDataSource {

    private static final ThreadLocal<Route> ROUTE = new ThreadLocal<>();

    public enum Route {
        PRIMARY, REPLICA
    }

    public static void clearReplicaRoute() {
        ROUTE.remove();
    }

    public static void setReplicaRoute() {
        ROUTE.set(Route.REPLICA);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return ROUTE.get();
    }
}
