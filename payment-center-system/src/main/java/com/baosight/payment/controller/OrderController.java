package com.baosight.payment.controller;

import com.baosight.database.page.PageRequest;
import com.baosight.payment.pojo.dto.PayOrderPageDTO;
import com.baosight.payment.pojo.vo.PayOrderPageVO;
import com.baosight.saas.constant.BaseUrlConstant;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseUrlConstant.SYSTEM + "/pm/pay/order/mange")
public class OrderController {

    /**
     * 获取支付中心订单了列表
     */
//    @PostMapping("/page")
//    public PageRequest<PayOrderPageVO> page(@RequestBody @Validated PayOrderPageDTO pageDTO){
//        return
//    }
}
