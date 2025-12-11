package com.baosight.payment.order.pojo.dto;

import com.baosight.database.core.page.PageRequest;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayOrderPageDTO extends PageRequest {
    /**
     * 订单号
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 商户id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long mchId;

    /**
     * 商户编号
     */
    private String mchNo;


    /**
     * 商户名称
     */
    private String mchName;

    /**
     * 渠道code
     */
    private String channelCode;


    /**
     * 通知状态
     */
    private Integer notifyState;

}
