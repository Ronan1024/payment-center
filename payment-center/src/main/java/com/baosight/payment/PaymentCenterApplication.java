package com.baosight.payment;

//import com.baosight.saas.tenant.api.constant.TenantApiConstant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author L.J.Ran
 */

@EnableAsync
@EnableDiscoveryClient
@ComponentScan({"com.baosight"})
//@EnableFeignClients({TenantApiConstant.PACKAGE})
@SpringBootApplication(scanBasePackages = "com.baosight.payment")
//@MapperScan({"com.baosight.payment.*.dao.mapper", "com.baosight.payment.*.mapper"})
public class PaymentCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentCenterApplication.class, args);
    }
}