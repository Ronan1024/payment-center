package com.baosight.payment.check.pojo.dto;

import com.baosight.database.page.PageRequest;
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
public class ChannelBillFilePageDTO extends PageRequest {
    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 渠道id
     */
    private Long channelId;


    /**
     * 账单日期
     */
    private String billDate;
}
