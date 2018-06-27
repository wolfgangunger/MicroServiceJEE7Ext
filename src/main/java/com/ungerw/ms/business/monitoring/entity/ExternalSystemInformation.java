package com.ungerw.ms.business.monitoring.entity;

/**
 * Information about an external system
 */
public class ExternalSystemInformation {

  private String systemName;
  private ExternalSystemState state;
  private String endpoint;
  private String user;
  private String errorMessage;

  public ExternalSystemState getState() {
    return state;
  }

  public void setState(final ExternalSystemState state) {
    this.state = state;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(final String endpoint) {
    this.endpoint = endpoint;
  }

  public String getUser() {
    return user;
  }

  public void setUser(final String user) {
    this.user = user;
  }

  public String getSystemName() {
    return systemName;
  }

  public void setSystemName(final String systemName) {
    this.systemName = systemName;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(final String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
