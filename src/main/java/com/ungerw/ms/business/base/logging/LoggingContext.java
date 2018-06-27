package com.ungerw.ms.business.base.logging;

import com.ungerw.ms.business.base.util.RequestIdUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Provides functionality to fill the logging MDC with USP-specific data.
 * <p/>
 * Usage is thread-safe. Can be used as auto-closeable.
 *
 * @author Eddie + Michael Baumgartner
 * @see org.slf4j.MDC
 * @since 27.11.13
 */
public class LoggingContext implements AutoCloseable {

  private static final Logger LOG = LoggerFactory.getLogger(LoggingContext.class);

  public static final String HASH_NOT_AVAILABLE = "n/a";

  private static final String SET_MARKER_KEY = "set_marker";
  private static final String USER_KEY = "user";
  private static final String USER_HASH_KEY = "userHash";
  private static final String REQUEST_ID_KEY = LoggingConstants.REQUEST_ID;
  private static final int HASH_LENGTH = 8;
  private static final int ONLY_LOW_8_BITS = 0xff;

  private final RequestIdUtil requestIdUtil = new RequestIdUtil();

  private static final ThreadLocal<MessageDigest> MESSAGE_DIGEST_STORAGE = new ThreadLocal<>();
  private static final Map<String, String> USER_MAP = Collections.synchronizedMap(new HashMap<>());

  private boolean outermost;

  public static String calculateShortHashFor(final String text) {
    final String hash = HASH_NOT_AVAILABLE;

    if (StringUtils.isEmpty(text)) {
      return hash;
    }
    return encodeHexStringAndResizeHash(text, hash, calculateMD5(MESSAGE_DIGEST_STORAGE.get()));
  }

  private static String encodeHexStringAndResizeHash(final String text, final String hash, final MessageDigest md) {
    if (md == null) {
      return hash;
    }
    try {
      final byte[] bytesOfMessage = text.getBytes("UTF-8");
      final byte[] theDigest = md.digest(bytesOfMessage);
      String modifiedHash = encodeHexString(theDigest);

      if (modifiedHash.length() > HASH_LENGTH) {
        modifiedHash = modifiedHash.substring(modifiedHash.length() - HASH_LENGTH);
      }
      return modifiedHash;
    } catch (final UnsupportedEncodingException e) {
      LOG.warn("Unexpected: digest does not support UTF-8", e);
    }
    return hash;
  }

  private static String encodeHexString(final byte[] bytes) {
    return IntStream.range(0, bytes.length).collect(StringBuilder::new,
            (sb, i) -> new Formatter(sb).format("%02x", bytes[i] & ONLY_LOW_8_BITS), StringBuilder::append).toString();
  }

  private static MessageDigest calculateMD5(final MessageDigest md) {
    if (md != null) {
      return md;
    }
    try {
      final MessageDigest modifiedDigest = MessageDigest.getInstance("MD5");
      MESSAGE_DIGEST_STORAGE.set(modifiedDigest);
      return modifiedDigest;
    } catch (final NoSuchAlgorithmException e) {
      LOG.warn("Unexpected: cannot find MessageDigest for algorithm MD5'", e);
    }
    return null;
  }

  private static String getHashForUser(final String user) {
    String userHash = USER_MAP.get(user);
    if (userHash == null) {
      userHash = calculateShortHashFor(user);
      USER_MAP.put(user, userHash);
    }
    return userHash;
  }

  public void close() {
    if (outermost) {
      MDC.remove(USER_KEY);
      MDC.remove(USER_HASH_KEY);
      MDC.remove(REQUEST_ID_KEY);
      MDC.remove(SET_MARKER_KEY);
    }
  }

  public LoggingContext(final String user, final String presetRequestId) {
    if (MDC.get(SET_MARKER_KEY) == null) {

      String requestId = presetRequestId;
      if (requestId == null || requestId.isEmpty()) {
        requestId = requestIdUtil.generateRequestId();
      }

      outermost = true;
      MDC.put(SET_MARKER_KEY, "set");

      MDC.put(USER_KEY, user);
      MDC.put(USER_HASH_KEY, getHashForUser(user));
      MDC.put(REQUEST_ID_KEY, requestId);
    }
  }

  public String getUser() {
    return MDC.get(USER_KEY);
  }
}
