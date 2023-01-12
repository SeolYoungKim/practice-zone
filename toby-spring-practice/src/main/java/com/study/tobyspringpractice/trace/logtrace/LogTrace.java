package com.study.tobyspringpractice.trace.logtrace;

import com.study.tobyspringpractice.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);
    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}
