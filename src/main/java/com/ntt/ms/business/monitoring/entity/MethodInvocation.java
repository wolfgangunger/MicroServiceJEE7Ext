//======================================================================================================================
// Copyright (c) 2011-2015 BMW Group. All rights reserved.
//======================================================================================================================
package com.ntt.ms.business.monitoring.entity;

import java.lang.reflect.Method;

/**
 * A transient BE.
 * <p/>
 * Transports {@link MethodInvocation} data and contains methods for measurement
 * and performance comparison.
 *
 * @author adam-bien.com
 */
public class MethodInvocation implements Comparable<MethodInvocation> {

  private String methodName;
  private long duration;
  private String exception;

  private long startTime;

  public MethodInvocation(Method methodName) {
    this.methodName = methodName.toGenericString();
  }

  public MethodInvocation(String methodName) {
    this.methodName = methodName;
  }

  public MethodInvocation() {
  }

  public void start() {
    this.startTime = System.currentTimeMillis();
  }

  public void end() {
    this.duration = System.currentTimeMillis() - this.startTime;
  }

  public long getCreationTime() {
    return this.startTime;
  }

  public void setException(Exception exception) {
    this.exception = exception.toString();
  }

  public String getMethodName() {
    return methodName;
  }

  public long getDuration() {
    return duration;
  }

  public String getException() {
    return exception;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  @Override
  public int compareTo(MethodInvocation other) {
    return Long.valueOf(this.duration).compareTo(other.duration);
  }

  public boolean hasException() {
    return this.exception != null;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 43 * hash + (this.methodName != null ? this.methodName.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final MethodInvocation other = (MethodInvocation) obj;
    if ((this.methodName == null) ? (other.methodName != null) : !this.methodName.equals(other.methodName)) {
      return false;
    }
    return true;
  }

  public boolean slowerThan(MethodInvocation other) {
    final boolean result;
    if (other == null) {
      result = true;
    } else {
      result = this.duration > other.duration;
    }
    return result;
  }

  @Override
  public String toString() {
    return "MethodInvocation{" + "methodName=" + methodName
        + ", duration=" + duration + ", exception=" + exception + ", startTime=" + startTime + '}';
  }
}
