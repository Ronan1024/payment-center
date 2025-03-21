package com.baosight.payment.notify.service;

import com.baosight.payment.notify.pojo.entity.PayMchNotifyRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_notify_record(商户通知记录表)】的数据库操作Service
 * @createDate 2025-03-20 16:37:35
 */
public interface PayMchNotifyRecordService extends IService<PayMchNotifyRecord> {

    /**
     * 获取支付通知记录
     *
     * @param notifyId 通知id
     */
    PayMchNotifyRecord infoId(Long notifyId);
}
