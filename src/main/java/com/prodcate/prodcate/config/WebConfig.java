package com.prodcate.prodcate.config;

import com.prodcate.prodcate.interceptor.MdcMethodInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final MdcMethodInfoInterceptor mdcMethodInfoInterceptor;

    public WebConfig(MdcMethodInfoInterceptor mdcMethodInfoInterceptor) {
        this.mdcMethodInfoInterceptor = mdcMethodInfoInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(mdcMethodInfoInterceptor);
    }
}