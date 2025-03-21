package com.baosight.payment.api;

import com.baosight.payment.vo.ApiPayWayVO;

import java.util.List;

public interface PayWayApi {

    /**
     * 获取应用支付方式
     * @param id
     * @param mchId
     * @param appId
     * @return
     */
    List<ApiPayWayVO> payWayList(Integer id, Long mchId, Long appId);
}
