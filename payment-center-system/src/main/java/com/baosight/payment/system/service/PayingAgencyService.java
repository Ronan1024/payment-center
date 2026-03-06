package com.baosight.payment.system.service;

import com.baosight.payment.system.pojo.dto.resp.PayingAgencyRespDTO;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
public interface PayingAgencyService {
    /**
     * 获取支付机构列表
     */
    List<PayingAgencyRespDTO> payingAgencyList();
}
