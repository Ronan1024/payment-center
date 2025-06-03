package com.baosight.payment.notify.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.enums.NotifyState;
import com.baosight.payment.notify.mapper.PayMchNotifyRecordMapper;
import com.baosight.payment.notify.pojo.entity.PayMchNotifyRecord;
import com.baosight.payment.notify.service.PayMchNotifyRecordService;
import com.baosight.utils.json.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_notify_record(商户通知记录表)】的数据库操作Service实现
 * @createDate 2025-03-20 16:37:35
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayMchNotifyRecordServiceImpl extends ServiceImpl<PayMchNotifyRecordMapper, PayMchNotifyRecord> implements PayMchNotifyRecordService {

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

    /**
     * 更新通知状态
     *
     * @param notifyId    异步通知id
     * @param notifyState 异步通知状态
     * @param res         通知系统返回信息
     */
    @Override
    public Boolean updateNotifyResult(Long notifyId, NotifyState notifyState, String res) {
        // 获取已有记录
        PayMchNotifyRecord payMchNotifyRecord = payMchNotifyRecordMapper.selectById(notifyId);
        Integer index = payMchNotifyRecord.getNotifyCount();
        payMchNotifyRecord.setNotifyCount(index + 1);
        if (!ObjectUtils.isEmpty(payMchNotifyRecord)) {
            if (StringUtils.hasText(res)) {
                List<String> list = new ArrayList<>();
                if (StringUtils.hasText(payMchNotifyRecord.getResResult())) {
                    JsonNode jsonNode = JsonUtil.readTree(payMchNotifyRecord.getResResult());
                    jsonNode.forEach(e -> list.add(JsonUtil.parse(e.asText(), String.class)));
                    list.add(res);
                }
                payMchNotifyRecord.setResResult(JsonUtil.toJson(list));
            }
            payMchNotifyRecord.setState(notifyState.getCode());
            return payMchNotifyRecordMapper.updateById(payMchNotifyRecord) > 0;
        }
        log.error("当前通知记录不存在：{}", notifyId);
        return Boolean.FALSE;
    }

    @Data
    private static class NotifyResponseDAO {
        /**
         * 序号
         */
        private Integer index;

        /**
         * 时间
         */
        private Date time;

        /**
         * 状态
         */
        private Integer state;

        /**
         * 返回结果
         */
        private String resResult;
    }
}




