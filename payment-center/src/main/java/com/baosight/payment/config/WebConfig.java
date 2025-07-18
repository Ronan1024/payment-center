package com.baosight.payment.config;

import com.baosight.saas.interceptor.SystemInterceptor;
import com.baosight.saas.oauth.api.ResourceApi;
import com.baosight.saas.properties.IgnoreProperties;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import static com.baosight.saas.constant.BaseUrlConstant.SYSTEM;

@Configuration
@EnableConfigurationProperties(IgnoreProperties.class)
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private IgnoreProperties ignoreProperties;

    @Lazy
    @Resource
    private ResourceApi resourceApi;

    @Bean
    public HandlerInterceptor systemInterceptor() {
        BiFunction<Long, String, Map<String, Set<String>>> function = (userId, type) -> resourceApi.getUserUrl(userId);
        return new SystemInterceptor(function, ignoreProperties);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(systemInterceptor()).addPathPatterns(SYSTEM + "/**").order(10);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
