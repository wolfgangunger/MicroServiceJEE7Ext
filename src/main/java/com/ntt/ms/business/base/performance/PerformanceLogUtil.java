package com.ntt.ms.business.base.performance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * PerformanceLogUtil.
 *
 * @author Krzysztof Wolf
 * @since 10/21/15
 */

@ApplicationScoped
public class PerformanceLogUtil implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final String PERFORMANCE_LOGGER_NAME = "com.ntt.sample.project.logging.PerformanceLogging";
  private static final String SEPARATOR = "|";
  private static final String NORMAL_ENTER_LOG_STRING = "ENTER";
  private static final String NORMAL_EXIT_LOG_STRING = "EXIT";

  public String buildEnterMessageForExternalCall(final String systemName, final String url) {
    return buildEnterMessage(PerformanceCategory.EXTERNAL_CALL, systemName, url);
  }

  public String buildExitMessageForExternalCall(final String systemName, final String url, final long selfTime, final long methodTime) {
    return buildExitMessage(PerformanceCategory.EXTERNAL_CALL, systemName, url, selfTime, methodTime);
  }

  public String buildEnterMessage(final PerformanceCategory performanceCategory, final String className,
                                  final String methodName) {
    final StringBuilder stringBuilder = buildMessage(performanceCategory, className, methodName);
    stringBuilder.append(NORMAL_ENTER_LOG_STRING);
    stringBuilder.append(SEPARATOR);
    return stringBuilder.toString();
  }

  public String buildExitMessage(final PerformanceCategory performanceCategory, final String className,
                                 final String methodName, final long selfTime, final long methodTime) {
    final StringBuilder stringBuilder = buildMessage(performanceCategory, className, methodName);
    stringBuilder.append(NORMAL_EXIT_LOG_STRING);
    stringBuilder.append(SEPARATOR);
    stringBuilder.append(methodTime);
    stringBuilder.append(SEPARATOR);
    stringBuilder.append(selfTime);
    stringBuilder.append(SEPARATOR);
    return stringBuilder.toString();
  }

  public Logger getInterceptorLogger(final PerformanceCategory performanceCategory) {
    final StringBuilder sb = new StringBuilder(PERFORMANCE_LOGGER_NAME);
    sb.append(".").append(performanceCategory.name());
    return LoggerFactory.getLogger(sb.toString());
  }

  private StringBuilder buildMessage(final PerformanceCategory performanceCategory, final String className,
                                     final String methodName) {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(performanceCategory.name());
    stringBuilder.append(SEPARATOR);
    stringBuilder.append(className);
    stringBuilder.append(SEPARATOR);
    stringBuilder.append(methodName);
    stringBuilder.append(SEPARATOR);

    return stringBuilder;
  }

  public LogPerformance getAnnotation(final Method method) {
    LogPerformance annotation = method.getAnnotation(LogPerformance.class);
    if (annotation == null) {
      annotation = method.getDeclaringClass().getAnnotation(LogPerformance.class);
    }
    return annotation;
  }

  public static PerformanceCategory getCategory(final LogPerformance performanceMethodLogging) {
    PerformanceCategory categoryLogging = PerformanceCategory.UNKNOWN;
    if (performanceMethodLogging != null) {
      categoryLogging = performanceMethodLogging.value();
    }
    return categoryLogging;
  }

  public static long calculateTimePassed(final long startTimeMillis) {
    final long timePassed = (System.currentTimeMillis() - startTimeMillis);
    return timePassed;
  }

}
