package com.baosight.payment.api;

import com.baosight.payment.dao.resp.AllInRespDTO;

/**
 * 获取系统基础配置信息
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/7
 */
public interface PlatformConfigurationApi {

    /**
     * 获取通联支付所有支付配置信息
     * @return
     */
    AllInRespDTO allInPayConfig();
}
