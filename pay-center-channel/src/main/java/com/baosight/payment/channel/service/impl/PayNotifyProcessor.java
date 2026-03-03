package com.baosight.payment.channel.service.impl;

import com.baosight.payment.channel.pojo.dao.UnifiedPayNotifyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 接受回调通知后，改变mall商城订单状态
 */
@Service
@RequiredArgsConstructor
public class PayNotifyProcessor {


    /**
     * 处理回调结果，改变mall订单状态
     * @param dto
     */
    public Boolean process(UnifiedPayNotifyDTO dto) {
        // TODO 1.更新订单状态 / 业务侧逻辑
        return true;
    }

}
