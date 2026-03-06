package com.baosight.payment.system.service.impl;

import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.system.pojo.dto.resp.PayingAgencyRespDTO;
import com.baosight.payment.system.service.PayingAgencyService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/3
 */
@Service
public class PayingAgencyServiceImpl implements PayingAgencyService {

    /**
     * 获取支付机构列表
     */
    @Override
    public List<PayingAgencyRespDTO> payingAgencyList() {
        return Arrays.stream(PayingAgency.values()).map(e -> {
            PayingAgencyRespDTO result = new PayingAgencyRespDTO();
            result.setCode(e.code());
            result.setId(e.getAgencyCode());
            result.setName(e.name());
            return result;
        }).toList();
    }
}
