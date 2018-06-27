//======================================================================================================================
// Copyright (c) 2011-2015 BMW Group. All rights reserved.
//======================================================================================================================
package com.ungerw.ms.business.monitoring.control;


import com.ungerw.ms.business.monitoring.entity.MethodInvocation;
import java.io.Serializable;
import java.util.Comparator;

/**
 * @author adam-bien.com
 */
public class TimeStampIncreasingComparator implements Comparator<MethodInvocation>, Serializable {
  private static final long serialVersionUID = 1L;

    @Override
    public int compare(final MethodInvocation first, final MethodInvocation next) {
        return Long.valueOf(first.getCreationTime()).compareTo(next.getCreationTime());
    }

}
