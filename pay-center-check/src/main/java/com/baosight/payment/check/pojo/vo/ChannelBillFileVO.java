package com.baosight.payment.check.pojo.vo;

import com.baosight.database.base.BasePO;
import com.baosight.web.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelBillFileVO extends BasePO {
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 账单日期
     */
    private String billDate;

    /**
     * 解析状态
     */
    private Boolean parseState;

    /**
     * 渠道账单code 渠道-日期组成
     */
    private String channelBillCode;

    /**
     * 解析异常
     */
    private String parseError;

    /**
     * 渠道id
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long channelId;

    /**
     * 渠道code
     */
    private String channelCode;
}
