package com.ntt.ms.business.base.performance;

/**
 * Describes the layer for the performance logging.
 * 
 * @author BAUMGM
 * @since 19.11.2015
 */
public enum PerformanceCategory {

  /*
   * ESI - identifies call made to the ESI layer from in e.g. BOUNDARY, SERVICE. EXTERNAL_CALL - identifies the call is
   * made by the ESI to the external system.
   */
  BOUNDARY, SERVICE, ENTITY, ESI, EXTERNAL_CALL, TIMER, RESOURCE, UNKNOWN
}
