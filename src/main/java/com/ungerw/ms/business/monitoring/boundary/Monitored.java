package com.ungerw.ms.business.monitoring.boundary;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

/**
 * Annotation to mark a class as being monitored.
 * 
 * @author ZORNAL
 * @since 17.08.15
 */
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface Monitored {
}
