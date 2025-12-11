package com.baosight.payment.config;

import com.baosight.web.core.path.IPathMatch;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/12/11
 */
@Configuration
public class PaymentCenterConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Bean
    public IPathMatch pathMatch() {
        return () -> {
            List<IPathMatch.Api> result = new ArrayList<>();
            result.add(IPathMatch.Api.create("system", "com.baosight.payment.*.controller.system"));
            return result;
        };
    }
}
