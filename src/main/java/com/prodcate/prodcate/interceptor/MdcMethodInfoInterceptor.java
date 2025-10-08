package com.prodcate.prodcate.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MdcMethodInfoInterceptor implements HandlerInterceptor {

    private static final String CLASS_NAME_KEY = "handlerClass";
    private static final String METHOD_NAME_KEY = "handlerMethod";

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {

        if (handler instanceof HandlerMethod handlerMethod) {
            String className = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();

            MDC.put(CLASS_NAME_KEY, className);
            MDC.put(METHOD_NAME_KEY, methodName);
        }
    }
}