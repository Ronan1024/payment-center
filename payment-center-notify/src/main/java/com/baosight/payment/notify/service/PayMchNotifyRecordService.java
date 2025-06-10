package com.baosight.payment.notify.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.enums.NotifyState;
import com.baosight.payment.notify.pojo.entity.PayMchNotifyRecord;
import com.baosight.payment.notify.pojo.vo.PayMchNotifyRecordVO;

import java.util.Date;
import java.util.List;

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

    /**
     * 更新通知状态
     *
     * @param notifyId    异步通知id
     * @param notifyState 异步通知状态
     * @param res         通知系统返回信息
     * @param notifyUrl   通知地址
     */
    Boolean updateNotifyResult(Long notifyId, NotifyState notifyState, String res, String notifyUrl);


    /**
     * 获取满足通知条件的记录信息
     *
     * @param now 当前时间
     */
    List<PayMchNotifyRecordVO> mchNotifyRecordList(Date now);
}
