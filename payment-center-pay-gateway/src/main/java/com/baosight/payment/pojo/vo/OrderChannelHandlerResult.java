package com.baosight.payment.pojo.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;

/**
 * 上游渠道响应信息包装类
 *
 * @author L.J.Ran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderChannelHandlerResult extends ChannelHandlerResult {

    /**
     * 支付订单状态
     */
    private Integer payOrderState;
    /**
     * 渠道订单号
     */
    private String channelOrderNo;

    private Object response;

    private ResponseEntity responseEntity;

}
