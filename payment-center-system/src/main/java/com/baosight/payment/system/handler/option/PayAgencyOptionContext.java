package com.baosight.payment.system.handler.option;

import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: payment-center
 * @description: 支付机构操作管理器
 * @author: L.J.Ran
 * @create: 2025/3/25
 */
@Configuration
public class PayAgencyOptionContext {
    private final ConcurrentHashMap<String, IPayAgencyOption> map = new ConcurrentHashMap<>();

    PayAgencyOptionContext(List<IPayAgencyOption> payAgencyOptionList) {
        payAgencyOptionList.forEach(option -> map.put(option.mark(), option));
    }


    public IPayAgencyOption option(String mark) {
        if (map.containsKey(mark)) {
            return map.get(mark);
        }
        throw new IllegalArgumentException("当前执行的支付机构操作不存在");
    }
}
