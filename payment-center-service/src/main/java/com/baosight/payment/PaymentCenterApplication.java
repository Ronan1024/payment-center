package com.baosight.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients("com.baosight.saas")
@ComponentScan({"com.baosight.saas", "com.baosight.payment"})
public class PaymentCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentCenterApplication.class, args);
    }
}