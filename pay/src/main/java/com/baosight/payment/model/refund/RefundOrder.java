package com.baosight.payment.model.refund;

import com.baosight.payment.model.AbstractMchApp;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RefundOrder extends AbstractMchApp {

    /**
     * 商户订单号
     **/
    private String mchOrderNo;

    /**
     * 商户系统生成的退款单号
     **/
//    @NotBlank(message="商户退款单号不能为空")
    private String mchRefundNo;


    /**
     * 支付系统订单号
     **/
    private String payOrderId;


    /**
     * 异步通知地址
     **/
    private String notifyUrl;


    /**
     * 退款金额， 单位：分
     **/
//    @NotNull(message="退款金额不能为空")
//    @Min(value = 1, message = "退款金额请大于1分")
    private String refundAmount;

    /**
     * 营销退款金额
     */
    private String promotionAmount;

    /**
     * 退款原因
     **/
//    @NotBlank(message="退款原因不能为空")
    private String refundReason;


    /**
     * 特定渠道发起额外参数
     **/
    private String channelExtra;

    /**
     * 商户扩展参数
     **/
    private String extParam;
}
