package com.baosight.payment.accounting.pojo.vo;

import com.baosight.web.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * 对账批次列表视图对象
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
@Data
public class CheckBatchListVO {

    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 对账批次号由支付接口-商户号-时间组成
     */
    private String checkBatchCode;

    /**
     * 渠道code
     */
    private String channelCode;

    /**
     * 渠道code
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long channelId;

    /**
     * 渠道商户号
     */
    private String channelMchNo;

    /**
     * 批次状态
     */
    private Integer state;

    /**
     * 账单时间
     */
    private String billDate;
}
