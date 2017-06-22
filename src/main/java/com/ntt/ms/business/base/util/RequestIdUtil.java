package com.ntt.ms.business.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

import java.util.UUID;

/**
 * RequestIdUtil.
 *
 * @author Krzysztof Wolf
 * @since 25.09.2015
 */

@ApplicationScoped
public class RequestIdUtil {
  private static final String REQUEST_ID_GENERATED = "A new request id has been generated '%s'.";
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestIdUtil.class);

  public String generateRequestId() {
    final String requestId = UUID.randomUUID().toString();
    LOGGER.warn(String.format(REQUEST_ID_GENERATED, requestId));
    return requestId;
  }
}
