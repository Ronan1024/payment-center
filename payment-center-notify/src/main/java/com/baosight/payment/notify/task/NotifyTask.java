package com.baosight.payment.notify.task;

import com.baosight.payment.notify.mq.proudct.PayOrderMchNotifyProduce;
import com.baosight.payment.notify.pojo.vo.PayMchNotifyRecordVO;
import com.baosight.payment.notify.service.PayMchNotifyRecordService;
import com.xxl.job.core.handler.annotation.XxlJob;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 通知任务处理器
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/9
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NotifyTask {

    private final PayMchNotifyRecordService payMchNotifyRecordService;
    @Resource
    private PayOrderMchNotifyProduce payOrderMchNotifyProduce;

    /**
     * 扫描出需要进行通知的记录
     */
    @XxlJob("notify-task")
    public void NotifyScan() {
        Date date = new Date();
        long logId = date.getTime();
        log.info("开始处理需要进行通知的数据 ===== log_id【{}】", logId);
        List<PayMchNotifyRecordVO> payMchNotifyRecordVOList = payMchNotifyRecordService.mchNotifyRecordList(date);
        log.info("需要进行通知的数据条数:{} ===== log_id【{}】", payMchNotifyRecordVOList.size(), logId);
        payMchNotifyRecordVOList.forEach(e -> {
            SendResult sendResult = payOrderMchNotifyProduce.sendMessage(e.getId());
            if (!sendResult.getSendStatus().equals(SendStatus.SEND_OK)) {
                log.info("发送通知失败，通知ID：{}，发送状态：{}", e.getId(), sendResult.getSendStatus());
            }
        });
    }
}
