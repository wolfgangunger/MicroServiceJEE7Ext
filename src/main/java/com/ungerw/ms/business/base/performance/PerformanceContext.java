package com.ungerw.ms.business.base.performance;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author UNGERW
 */
@RequestScoped
public class PerformanceContext implements Serializable {
    private static final long serialVersionUID = 1L;

    private ConcurrentHashMap<String, Deque<MethodTiming>> methodTimingMap;

    @PostConstruct
    public void prepareContext() {
        methodTimingMap = new ConcurrentHashMap<>();
    }

    @PreDestroy
    public void destroyContext() {
        methodTimingMap.clear();
        methodTimingMap = null;
    }

    private Deque<MethodTiming> getForCurrentThread() {
        final String name = Thread.currentThread().getName();
        if (methodTimingMap.containsKey(name)) {
            return methodTimingMap.get(name);
        } else {
            final Deque<MethodTiming> timingStack = new LinkedList<>();
            methodTimingMap.put(name, timingStack);
            return timingStack;
        }

    }

    public void enter(final String methodName) {
        final Deque<MethodTiming> methodTimings = getForCurrentThread();
        final MethodTiming methodTiming = new MethodTiming();
        methodTiming.initPath(getCallerPath(methodTimings), methodName);
        pauseCaller(methodTimings);
        methodTiming.enter();
        methodTimings.push(methodTiming);
    }

    private void pauseCaller(final Deque<MethodTiming> methodTimings) {
        if (!methodTimings.isEmpty()) {
            methodTimings.peek().suspend();
        }
    }

    public MethodTiming exit() {
        final Deque<MethodTiming> methodTimings = getForCurrentThread();
        final MethodTiming methodTiming = methodTimings.pop();
        methodTiming.exit();
        resumeCaller(methodTimings);
        return methodTiming;
    }

    private void resumeCaller(final Deque<MethodTiming> methodTimings) {
        if (!methodTimings.isEmpty()) {
            methodTimings.peek().resume();
        }
    }

    private String getCallerPath(final Deque<MethodTiming> methodTimings) {
        if (!methodTimings.isEmpty()) {
            return methodTimings.peek().getPath();
        } else {
            return StringUtils.EMPTY;
        }
    }

    static class MethodTiming {
        private final StopWatch selfTime = new StopWatch();
        private final StopWatch methodTime = new StopWatch();
        private String path;

        public void enter() {
            selfTime.start();
            methodTime.start();
        }

        public void suspend() {
            selfTime.suspend();
        }

        public void resume() {
            selfTime.resume();
        }

        public void exit() {
            selfTime.stop();
            methodTime.stop();
        }

        public String getPath() {
            return path;
        }

        public void initPath(final String path, final String methodName) {
            this.path = path + "." + methodName;
        }

        public long getSelf() {
            return selfTime.getTime();
        }

        public long getMethod() {
            return methodTime.getTime();
        }
    }
}

