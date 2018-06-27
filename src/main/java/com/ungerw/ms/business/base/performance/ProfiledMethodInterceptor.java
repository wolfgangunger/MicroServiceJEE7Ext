/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ungerw.ms.business.base.performance;

import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author UNGERW
 */
@Profiled
@Interceptor
public class ProfiledMethodInterceptor {

    private static final Logger LOGGER = Logger.getLogger(ProfiledMethodInterceptor.class.getName());

    /**
     * starts a stopwatch before a given method execution and stops it after the
     * method is proceeded. Then logs the elapsed time.
     *
     * @param context the invocation context.
     * @return the object produced by the method execution.
     * @throws Exception if an error occurs.
     */
    @AroundInvoke
    public Object profile(InvocationContext context) throws Exception {

        // starts the stopwatch
        final long startTime = System.currentTimeMillis();

        // executes the method that is profiled
        Object o = context.proceed();

        // stops the stopwatch and computes elapsed time
        final long elapsedTime = System.currentTimeMillis() - startTime;

        // logs method and time
        final String methodSignature = context.getMethod().toString();
        LOGGER.info("Profiled Method: {} in {} ms" + methodSignature + " " +  elapsedTime);

        return o;
    }

}
