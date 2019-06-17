package com.wirecard.payment.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
@RequiredArgsConstructor
public class WebInterceptorConfiguration extends WebMvcConfigurerAdapter {


    private final ResponseHeadersConfiguration responseHeadersConfiguration;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(this.responseHeadersConfiguration);

        super.addInterceptors(registry);
    }
}
