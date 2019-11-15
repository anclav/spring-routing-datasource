package org.anclav.doubledatasource.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, показывающая, что запрос будет сделан в реплику, данные, которые вернет могут быть слегка подтухшими.
 * Будут ошибки если повесить на не read-only метод
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ReplicaQuery {
}
