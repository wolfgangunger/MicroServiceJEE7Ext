package com.ungerw.ms.business.base.performance;

/**
 * 
 * @author UNGERW
 */
public enum PerformanceCategory {

  /*
   * ESI - identifies call made to the ESI layer from in e.g. BOUNDARY, SERVICE. EXTERNAL_CALL - identifies the call is
   * made by the ESI to the external system.
   */
  BOUNDARY, SERVICE, ENTITY, ESI, EXTERNAL_CALL, TIMER, RESOURCE, UNKNOWN
}
