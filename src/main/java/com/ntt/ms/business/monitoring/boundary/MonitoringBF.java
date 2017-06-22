// ======================================================================================================================
// Copyright (c) 2011-2015 BMW Group. All rights reserved.
// ======================================================================================================================
package com.ntt.ms.business.monitoring.boundary;



import com.ntt.ms.business.base.boundary.AbstractBF;
import com.ntt.ms.business.monitoring.control.EntityMonitoringEventStore;
import com.ntt.ms.business.monitoring.control.MethodsMonitoringEventStore;
import com.ntt.ms.business.monitoring.entity.EntityMonitoring;
import com.ntt.ms.business.monitoring.entity.ExternalSystemInformation;
import com.ntt.ms.business.monitoring.entity.ExternalSystemState;
import com.ntt.ms.business.monitoring.entity.MethodInvocation;
import javax.ejb.Stateless;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The monitoring entry point.
 *
 * @author adam-bien.com
 */
@Stateless
@Path("/monitoring")
@Api(value = "/monitoring", description = "monitoring resource")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class MonitoringBF  extends AbstractBF{

  static final Logger LOG = LoggerFactory.getLogger(MonitoringBF.class);

  @Inject
  private MethodsMonitoringEventStore mes;

  @Inject
  private EntityMonitoringEventStore salesOrderMonitor;

  @Inject
  private Instance<Monitorable> monitors;

  @GET
  @Path("/methods/exceptional")
  @ApiOperation(value = "Monitor - methods exception", notes = "show executed methods with exceptions", response = MethodInvocation.class, responseContainer = "list")
  public List<MethodInvocation> exceptionalMethods() {
    return mes.getExceptional();
  }

  @GET
  @Path("/methods/slowest")
  @ApiOperation(value = "Monitor - methods slowest", notes = "show slowest executed methods", response = MethodInvocation.class, responseContainer = "list")
  public List<MethodInvocation> slowestMethods() {
    return mes.getSlowest();
  }

  @GET
  @Path("/methods/recent")
  @ApiOperation(value = "Monitor - methods recent", notes = "show recent executed methods", response = MethodInvocation.class, responseContainer = "list")
  public List<MethodInvocation> recentMethods() {
    return mes.getRecent();
  }

  @GET
  @Path("/entities/salesOrder")
  @ApiOperation(value = "Monitor - offers", notes = "offer entity monitoring", response = EntityMonitoring.class)
  public EntityMonitoring getOffersMonitoring() {
    return salesOrderMonitor.getDealerMonitor();
  }

  @GET
  @Path("/ping")
  @ApiOperation(value = "Monitor - ping", notes = "ping REST resource", response = Response.class)
  @Produces(MediaType.TEXT_PLAIN)
  public Response ping() {
    LOG.info("Ping! " + new Date().toString());

    return Response.ok().entity("ALIVE").build();
  }

  @GET
  @Path("/systems")
  @ApiOperation(value = "Monitor - systems", notes = "show the information about all external systems", responseContainer = "list", response = ExternalSystemInformation.class)
  public Response getStatus() {
    final List<ExternalSystemInformation> externalSystemInformation = new ArrayList<>();
    for (final Monitorable monitor : monitors) {
      externalSystemInformation.add(getExternalSystemInformationForSystem(monitor));
    }
    return Response.ok().entity(externalSystemInformation).build();
  }

  private ExternalSystemInformation getExternalSystemInformationForSystem(final Monitorable monitor) {
    try {
      return monitor.getExternalSystemInformation();

    } // CHECKSTYLE:OFF IllegalCatchCheck
    catch (final RuntimeException e) { // NOSONAR
      // CHECKSTYLE:ON
      final ExternalSystemInformation information = new ExternalSystemInformation();
      information.setSystemName(monitor.getSystem());
      information.setState(ExternalSystemState.ERROR);
      information.setErrorMessage(e.getMessage());
      return information;
    }
  }

  @GET
  @Path("/systems/{system}")
  @ApiOperation(value = "Monitor - system", notes = "show the information about one external systems", response = ExternalSystemInformation.class)
  public Response getStatusForSystem(@NotNull @PathParam("system") final String system) {
    ExternalSystemInformation information = null;
    for (final Monitorable monitor : monitors) {
      if (monitor.getSystem().equalsIgnoreCase(system)) {
        information = getExternalSystemInformationForSystem(monitor);
      }
    }
    return Response.ok().entity(information).build();
  }

  @GET
  @Path("/systems/systemlist")
  @ApiOperation(value = "Monitor - system list", notes = "shows the list of all external systems available", responseContainer = "list", response = String.class)
  public Response getSystemList() {
    final List<String> states = new ArrayList<>();
    for (final Monitorable monitor : monitors) {
      states.add(monitor.getSystem());
    }
    return Response.ok().entity(states).build();
  }
}
