package com.prodcate.prodcate.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private static final String CORRELATION_ID_KEY = "correlationId";
    private static final String CLASS_NAME_KEY = "handlerClass"; // Interceptor'dan gelen anahtar
    private static final String METHOD_NAME_KEY = "handlerMethod"; // Interceptor'dan gelen anahtar
    private static final int MAX_PAYLOAD_LENGTH = 2048; // Maksimum loglanacak gövde boyutu

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        final long startTime = System.currentTimeMillis();
        String correlationId = getOrCreateCorrelationId(wrappedRequest);

        MDC.put(CORRELATION_ID_KEY, correlationId);

        try {

            filterChain.doFilter(wrappedRequest, wrappedResponse);

        } finally {
            long duration = System.currentTimeMillis() - startTime;

            logStructuredDetails(wrappedRequest, wrappedResponse, duration);

            wrappedResponse.copyBodyToResponse();

            MDC.remove(CORRELATION_ID_KEY);
            MDC.remove(CLASS_NAME_KEY);
            MDC.remove(METHOD_NAME_KEY);
        }
    }

    private void logStructuredDetails(ContentCachingRequestWrapper request,
                                      ContentCachingResponseWrapper response,
                                      long duration) {

        Map<String, Object> logData = new HashMap<>();

        logData.put("event", "api_request_finished");
        logData.put("method", request.getMethod());
        logData.put("uri", request.getRequestURI());
        logData.put("sourceIp", request.getRemoteAddr());
        logData.put("status", response.getStatus());
        logData.put("durationMs", duration);
        logData.put("requestParameters", getParamMap(request));
        logData.put("requestBody", getRequestBody(request));
        logData.put("responseBody", getResponseBody(response));

        logger.info("API request completed successfully.", logData);
    }

    private String getOrCreateCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader("X-Correlation-Id");
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString();
        }
        return correlationId;
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            int length = Math.min(content.length, MAX_PAYLOAD_LENGTH);
            try {
                return new String(content, 0, length, request.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                return "[Unsupported Encoding]";
            }
        }
        return "";
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            int length = Math.min(content.length, MAX_PAYLOAD_LENGTH);
            try {
                return new String(content, 0, length, response.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                return "[Unsupported Encoding]";
            }
        }
        return "";
    }

    private Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] values = request.getParameterValues(paramName);
            if (values != null && values.length > 0) {
                params.put(paramName, values[0]);
            }
        }
        return params.isEmpty() ? Collections.emptyMap() : params;
    }
}