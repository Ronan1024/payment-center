package com.baosight.payment.config;


import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author L.J.Ran
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class XxlJobConfig {

    @Value("${server.port}")
    private int port;
    @Value("${xxl.job.addresses}")
    private String addresses;
    @Value("${xxl.job.accessToken}")
    private String accessToken;
    @Value("${spring.application.name}")
    private String appName;
    @Value("${xxl.job.logPath}")
    private String logPath;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(addresses);
        xxlJobSpringExecutor.setAppname(appName);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath("./" + appName + logPath);
        xxlJobSpringExecutor.setIp(localHost.getHostAddress());
        xxlJobSpringExecutor.setPort(port + 1000);
        xxlJobSpringExecutor.setLogRetentionDays(30);
        log.info(">>>>>>>>>>> xxl-job config init. init Ip [{}].", localHost.getHostAddress());
        return xxlJobSpringExecutor;
    }


}
