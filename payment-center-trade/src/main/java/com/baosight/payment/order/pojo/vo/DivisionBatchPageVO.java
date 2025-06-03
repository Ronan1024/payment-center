package com.baosight.payment.order.pojo.vo;

import com.baosight.web.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/9
 */
@Data
public class DivisionBatchPageVO {

    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 分账批次时间
     */
    private String date;

    /**
     * 分账状态
     */
    private Integer divisionState;

    /**
     * 客户端id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long clientId;

    /**
     * 失败原因
     */
    private String failMsg;

    /**
     * 渠道分账id
     */
    private String channelBatchId;
    /**
     * 渠道处理结果
     */
    private String channelHandlerResult;
}
