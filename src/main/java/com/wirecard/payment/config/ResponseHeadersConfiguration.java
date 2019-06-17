package com.wirecard.payment.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log
@RequiredArgsConstructor
@Component
public class ResponseHeadersConfiguration extends HandlerInterceptorAdapter {
    private static final String TRACE_ID = "TraceId";
    private final BeanFactory beanFactory;
    private Tracer tracer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        setTracerId(response);
        response.setHeader("Context-Path", request.getRequestURI());
        response.setHeader("session-id", request.getHeader("session-id"));
        return true;
    }

    private void setTracerId(HttpServletResponse response) {
        if (getTracer().isTracing() && StringUtils.isEmpty(response.getHeader(TRACE_ID))) {
            response.setHeader(TRACE_ID, Span.idToHex(getTracer().getCurrentSpan().getTraceId()));
        }
    }

    private Tracer getTracer() {
        if (tracer == null) {
            tracer = beanFactory.getBean(Tracer.class);
        }
        return tracer;
    }
}
