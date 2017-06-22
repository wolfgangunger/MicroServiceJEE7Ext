package com.ntt.ms.business.base.logging;

/**
 * Class for holding logging shared information
 *
 * @author XPOPOC
 * @since 5/21/2014
 */
public final class LoggingConstants {
  public static final String EXCEPTION_HANDLER_LOGGING_MSG = "[Code '%s'; Type '%s'; Name '%s'; Message '%s']";
  public static final String REQUEST_ID = "requestId";

  public static final String REQUEST_ID_EMPTY_MSG = "The http header '%s' is sent, but the value is empty.";
  public static final String REQUEST_ID_MULTIPLE_MSG = "Multiple request Ids '%s' found in the request. Using the first one '%s'.";
  public static final String REQUEST_ID_NO_HEADER_MSG = "Request Id http header '%s' not found.";

  private LoggingConstants() {
    // prevent instantiation
  }
}
