package com.ungerw.ms.business.monitoring.control;

import com.ungerw.ms.business.monitoring.entity.EntityMonitoring;
import com.ungerw.ms.business.sample.entity.SalesOrder;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;

/**
 * @author adam-bien.com
 * @author ZORNAL class is base on adam bien's monitoring in the CA4.X
 * @since 10.10.15
 */
@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class EntityMonitoringEventStore {

    private EntityMonitoring salesOrderMonitor;

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(EntityMonitoringEventStore.class.getName());

    @PostConstruct
    public void init() {
        this.salesOrderMonitor = new EntityMonitoring();
    }

    @Schedule(minute = "*/1", hour = "*")
    public void compute() {
        this.salesOrderMonitor.computeStats();
        LOGGER.info("Computing monitoring: " + this.salesOrderMonitor);
    }

    public void onSuccessfulCreation(final @Observes(during = TransactionPhase.AFTER_SUCCESS) SalesOrder sa) {
        this.salesOrderMonitor.newCreation();
    }

    public void onFailedCreation(final @Observes(during = TransactionPhase.AFTER_FAILURE) SalesOrder sa) {
        this.salesOrderMonitor.newFailure();
    }

    public EntityMonitoring getDealerMonitor() {
        return salesOrderMonitor;
    }
}
