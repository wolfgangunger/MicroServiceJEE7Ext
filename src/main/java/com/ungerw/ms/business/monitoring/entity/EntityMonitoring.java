package com.ungerw.ms.business.monitoring.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.concurrent.atomic.AtomicLong;


import io.swagger.annotations.ApiModelProperty;

/**
 * @author adam-bien.com
 * @author ZORNAL class is base on adam bien's monitoring in the CA4.X
 * @since 10.10.15
 */
@XmlRootElement(name = "monitoring")
@XmlAccessorType(XmlAccessType.FIELD)
public final class EntityMonitoring {

  public static final int PROBES_PER_HOUR = 60;
  @XmlTransient
  private final AtomicLong creationCounter;
  @XmlTransient
  private final AtomicLong failureCounter;

  private long lastCreationCounter;
  private long lastFailureCounter;
  private long creationsPerHour;
  private long failuresPerHour;

  @XmlTransient
  private final int probesPerHour;

  public EntityMonitoring() {

    this.probesPerHour = PROBES_PER_HOUR; // for per hour estimation
    this.creationCounter = new AtomicLong(0);
    this.failureCounter = new AtomicLong(0);
  }

  public void newCreation() {
    this.creationCounter.incrementAndGet();
  }

  public void newFailure() {
    this.failureCounter.incrementAndGet();
  }

  public void computeStats() {
    final long currentCreationCounter = creationCounter.longValue();
    this.creationsPerHour = (currentCreationCounter - lastCreationCounter) * probesPerHour;
    this.lastCreationCounter = currentCreationCounter;

    final long currentFailureCounter = failureCounter.longValue();
    this.failuresPerHour = (currentFailureCounter - lastFailureCounter) * probesPerHour;
    this.lastFailureCounter = currentFailureCounter;
  }

  public long getCreationsPerHour() {
    return creationsPerHour;
  }

  public long getFailuresPerHour() {
    return failuresPerHour;
  }

  @XmlElement(name = "totalCreations")
  @JsonProperty(value = "totalCreations")
  @ApiModelProperty(name = "totalCreations", value = "totalCreations")
  public long getLastCreationCounter() {
    return lastCreationCounter;
  }

  @XmlElement(name = "totalFailures")
  @JsonProperty(value = "totalFailures")
  @ApiModelProperty(name = "totalFailures", value = "totalFailures")
  public long getLastFailureCounter() {
    return lastFailureCounter;
  }

  @Override
  public String toString() {
    return "EntityMonitoring{" + "creationCounter=" + creationCounter + ", failureCounter=" + failureCounter
            + ", lastCreationCounter=" + lastCreationCounter + ", lastFailureCounter=" + lastFailureCounter
            + ", creationsPerHour=" + creationsPerHour + ", failuresPerHour=" + failuresPerHour + ", probesPerHour="
            + probesPerHour + '}';
  }
}
