package com.ungerw.ms.business.base.performance;

import com.ungerw.ms.business.base.logging.LoggingContext;
import org.slf4j.Logger;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

/**
 * 
 * @author UNGERW
 */
@Interceptor
@LogPerformance
@ManagedBean
public class PerformanceInterceptor implements Serializable {

  private static final long serialVersionUID = -8579765632603670719L;

  @Inject
  PerformanceLogUtil performanceLogUtil;

  @Inject
  PerformanceContext performanceContext;

  @SuppressWarnings("squid:S00112") // ok because of invocationContext.proceed signature &
  // exception handling done in
  // ExceptionInterceptor
  @AroundInvoke
  public Object intercept(final InvocationContext invocationContext) throws Exception {
    final LogPerformance annotation = performanceLogUtil.getAnnotation(invocationContext.getMethod());
    return logAndProceed(invocationContext, annotation);
  }

  @SuppressWarnings("squid:S00112") // ok because of invocationContext.proceed signature &
  // exception handling done in
  // ExceptionInterceptor
  @AroundTimeout
  public Object interceptTimer(final InvocationContext invocationContext) throws Exception {
    try (LoggingContext loggingContext = new LoggingContext(null, null)) {
      final LogPerformance annotation = performanceLogUtil.getAnnotation(invocationContext.getMethod());
      return logAndProceed(invocationContext, annotation);
    }
  }

  @SuppressWarnings({"squid:S00112", "squid:S1181"}) // ok because of invocationContext.proceed signature &
  // exception handling done in
  // ExceptionInterceptor
  private Object logAndProceed(final InvocationContext invocationContext, final LogPerformance logPerformanceAnnotation)
          throws Exception {

    final String className = getClassName(invocationContext);
    final String methodName = invocationContext.getMethod().getName();
    // final long startTimeMillis = System.currentTimeMillis();
    performanceContext.enter(methodName);

    doLoggingEnter(className, methodName, logPerformanceAnnotation);

    try {
      final Object retValue = invocationContext.proceed();
      return retValue;
    }
    // CHECKSTYLE:OFF IllegalCatchCheck
    catch (final Exception exception) {
      // CHECKSTYLE:ON
      throw exception;
    } finally{
      final PerformanceContext.MethodTiming methodTiming = performanceContext.exit();
      doLoggingExit(className, methodName, logPerformanceAnnotation, methodTiming.getSelf(), methodTiming.getMethod());
    }
  }

  private void doLoggingEnter(final String className, final String methodName,
                              final LogPerformance performanceMethodLogging) {
    final PerformanceCategory performanceCategory = PerformanceLogUtil.getCategory(performanceMethodLogging);
    final Logger log = performanceLogUtil.getInterceptorLogger(performanceCategory);
    if (!log.isInfoEnabled()) {
      return;
    }
    final String enterMessage = performanceLogUtil.buildEnterMessage(performanceCategory, className, methodName);
    log.info(enterMessage);
  }

  private String getClassName(final InvocationContext ic) {
    if (ic.getTimer() != null) {
      return ic.getTarget().getClass().getName();
    } else {
      return ic.getMethod().getDeclaringClass().getName();
    }
  }

  private void doLoggingExit(final String className, final String methodName, final LogPerformance performanceMethodLogging, final long selfTime, final long methodTime) {
    final PerformanceCategory performanceCategory = PerformanceLogUtil.getCategory(performanceMethodLogging);
    final Logger log = performanceLogUtil.getInterceptorLogger(performanceCategory);
    if (!log.isInfoEnabled()) {
      return;
    }
    final String exitMessage = performanceLogUtil.buildExitMessage(performanceCategory, className, methodName, selfTime, methodTime);
    log.info(exitMessage);
  }

}