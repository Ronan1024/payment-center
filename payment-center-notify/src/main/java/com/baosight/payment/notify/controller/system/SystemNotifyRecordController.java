package com.baosight.payment.notify.controller.system;

import com.baosight.payment.notify.pojo.vo.OrderNotifyRecordVO;
import com.baosight.payment.notify.service.PayMchNotifyRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 平台通知记录管理
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/20
 */
@RequiredArgsConstructor
@RestController
//@RequestMapping(SYSTEM + "/notify/record/manage")
@RequestMapping("/notify/record/manage")
public class SystemNotifyRecordController {

    private final PayMchNotifyRecordService payMchNotifyRecordService;

    /**
     * 获取指定订单的通知记录信息
     *
     * @param orderId 订单id
     */
    @GetMapping("/order_notify_info")
    public OrderNotifyRecordVO orderNotifyRecordInfo(@RequestParam("orderId") Long orderId) {
        return payMchNotifyRecordService.orderNotifyRecordInfo(orderId);
    }
}
