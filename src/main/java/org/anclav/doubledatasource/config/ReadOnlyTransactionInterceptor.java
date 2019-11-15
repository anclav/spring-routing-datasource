package org.anclav.doubledatasource.config;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ReadOnlyTransactionInterceptor {

    @Around("@annotation(replicaQuery)")
    public Object proceed(ProceedingJoinPoint pjp, ReplicaQuery replicaQuery) throws Throwable {
        try {
            RoutingDatasource.setReplicaRoute();
            return pjp.proceed();
        } finally {
            RoutingDatasource.clearReplicaRoute();
        }
    }
}
