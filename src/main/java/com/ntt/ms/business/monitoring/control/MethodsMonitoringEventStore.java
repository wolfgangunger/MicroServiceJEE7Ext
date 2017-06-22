//======================================================================================================================
// Copyright (c) 2011-2015 BMW Group. All rights reserved.
//======================================================================================================================
package com.ntt.ms.business.monitoring.control;


import com.ntt.ms.business.base.security.AccountSecurityInterceptor;
import com.ntt.ms.business.monitoring.entity.MethodInvocation;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Transient {@link MethodInvocation} statistics storage. Computes on-the-fly the slowest methods and also manages the
 * "exceptional" and recent invocations.
 *
 * @author adam-bien.com
 */
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class MethodsMonitoringEventStore {

  private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(MethodsMonitoringEventStore.class.getName());
      
  private Map<String, MethodInvocation> invocations;
  private Map<String, MethodInvocation> exceptional;
  private Map<String, MethodInvocation> slowest;

  @PostConstruct
  public void initialize() {
    this.invocations = new ConcurrentHashMap<>();
    this.exceptional = new ConcurrentHashMap<>();
    this.slowest = new ConcurrentHashMap<>();
  }

  public void onMonitoring(@Observes
  MethodInvocation invocation) {
    final String methodName = invocation.getMethodName();
    this.invocations.put(methodName, invocation);
    addSlowest(invocation);
    if (invocation.hasException()) {
      this.exceptional.put(methodName, invocation);
    }
  }

  public List<MethodInvocation> getExceptional() {
    return new ArrayList<>(this.exceptional.values());
  }

  public List<MethodInvocation> getSlowest() {
    List<MethodInvocation> retVal = new ArrayList<>(this.slowest.values());
    Collections.sort(retVal);
    Collections.reverse(retVal);
    return retVal;
  }

  public List<MethodInvocation> getRecent() {
    List<MethodInvocation> list = new ArrayList<>(this.invocations.values());
    Collections.sort(list, new TimeStampIncreasingComparator());
    return list;
  }

  void addSlowest(MethodInvocation invocation) {
    String methodName = invocation.getMethodName();
    if (methodName == null) {
      LOGGER.info("Invocation is does not have a name: " + invocation);
      return;
    }
    final MethodInvocation current = this.slowest.get(methodName);
    if (invocation.slowerThan(current)) {
      this.slowest.put(methodName, invocation);
    }
  }

}
