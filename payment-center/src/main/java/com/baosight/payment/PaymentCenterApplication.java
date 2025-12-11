package com.baosight.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author L.J.Ran
 */
@EnableDiscoveryClient
@ComponentScan({"com.baosight"})
//@EnableFeignClients("com.baosight.saas")
@SpringBootApplication(scanBasePackages = "com.baosight.payment")
public class PaymentCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentCenterApplication.class, args);
    }
}