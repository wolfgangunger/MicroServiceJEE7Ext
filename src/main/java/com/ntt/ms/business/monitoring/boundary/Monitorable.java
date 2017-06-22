package com.ntt.ms.business.monitoring.boundary;

import com.ntt.ms.business.monitoring.entity.ExternalSystemInformation;


/**
 * Interface for all systems to be monitored.
 *
 * It is supposed to be implemented for all external systems.
 *
 * @author Michael Baumgartner
 * @since 16.12.2015
 *
 */
public interface Monitorable {

  /**
   * The system key to identify the system.
   * @return id for the system
   */
  String getSystem();

  /**
   * Provide information about the external system, like status or endpoint.
   * @return status information about the system
   */
  ExternalSystemInformation getExternalSystemInformation();
}
