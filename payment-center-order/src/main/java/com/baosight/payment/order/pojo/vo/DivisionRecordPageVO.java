package com.baosight.payment.order.pojo.vo;

import com.baosight.database.base.BasePO;
import com.baosight.web.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DivisionRecordPageVO extends BasePO {
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 对账批次id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long batchId;

    /**
     * 订单id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long orderId;

    /**
     * 对账状态
     */
    private Integer state;

    /**
     * 异常信息
     */
    private String errMsg;

    /**
     * 对账时间
     */
    private String date;
}
