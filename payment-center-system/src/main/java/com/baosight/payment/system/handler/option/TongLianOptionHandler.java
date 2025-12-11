package com.baosight.payment.system.handler.option;

import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.system.enums.TongLianOption;
import com.baosight.payment.system.manager.PayInterfaceConfigManager;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.entity.PayTongLianRelevance;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/25
 */
@Component
@RequiredArgsConstructor
public class TongLianOptionHandler implements IPayAgencyOption<PayInterfaceConfig> {
    @Resource
    private PayInterfaceConfigManager payInterfaceConfigManager;

    @Override
    public String mark() {
        return PayingAgency.TONG_LIAN.code();
    }

    @Override
    public List<String> option(PayInterfaceConfig payInterfaceConfig) {
        PayTongLianRelevance relevanceInfo = payInterfaceConfigManager.tongLianRelevance(payInterfaceConfig.getClientId());
        if (!ObjectUtils.isEmpty(relevanceInfo)) {
            return Arrays.stream(TongLianOption.values()).filter(e -> e.getFunction().apply(relevanceInfo)).map(TongLianOption::getCode).toList();
        }
        return Collections.emptyList();
    }
}
