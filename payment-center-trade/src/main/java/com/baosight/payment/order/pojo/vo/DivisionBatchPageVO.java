package com.baosight.payment.order.pojo.vo;

import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/9
 */
@Data
public class DivisionBatchPageVO {

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
    private Long clientId;

    /**
     * 失败原因
     */
    private String failMsg;
}
