package com.baosight.payment.service;

import com.baosight.payment.model.payway.PayWayModel;

import java.util.List;

public interface ApiPayWayService {

    /**
     * 获取支付方式列表
     *
     * @param client 获取客户端id
     * @param appId  应用id
     * @param mchId  商户id
     */
    List<PayWayModel> paywayList(Integer client, Long appId, Long mchId);
}
