package com.baosight.payment.dao;

import com.baosight.payment.access.tl.model.TongLianIsvConfigDAO;

/**
 * @program: payment-center
 * @description: 通联服务商及商家配置信息
 * @author: L.J.Ran
 * @create: 2025/3/26
 */
public record TongLianIsvAndMchConfigDAO(TongLianIsvConfigDAO isvConfig, TongLianMchConfigDAO mchConfig){}
