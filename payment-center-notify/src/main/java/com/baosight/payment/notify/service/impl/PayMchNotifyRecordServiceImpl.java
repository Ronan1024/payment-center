package com.baosight.payment.notify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.notify.pojo.entity.PayMchNotifyRecord;
import com.baosight.payment.notify.service.PayMchNotifyRecordService;
import com.baosight.payment.notify.mapper.PayMchNotifyRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author longjiangran
* @description 针对表【pay_mch_notify_record(商户通知记录表)】的数据库操作Service实现
* @createDate 2025-03-20 16:37:35
*/
@Service
@RequiredArgsConstructor
public class PayMchNotifyRecordServiceImpl extends ServiceImpl<PayMchNotifyRecordMapper, PayMchNotifyRecord> implements PayMchNotifyRecordService{

    private final PayMchNotifyRecordMapper payMchNotifyRecordMapper;

    /**
     * 获取支付通知记录
     *
     * @param notifyId 通知id
     */
    @Override
    public PayMchNotifyRecord infoId(Long notifyId) {
        return payMchNotifyRecordMapper.selectById(notifyId);
    }
}




