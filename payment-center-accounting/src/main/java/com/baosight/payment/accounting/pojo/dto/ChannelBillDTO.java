package com.baosight.payment.accounting.pojo.dto;

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
public class ChannelBillDTO extends PageRequest {

    /**
     * 对账文件id
     */
    private Long billFileId;

    /**
     * 对账文件code
     */
    private String billFileCode;

    /**
     * 账单日期
     */
    private String billDate;

    /**
     * 支付渠道id
     */
    private Long channelId;

    /**
     * 支付渠道code
     */
    private String channelCode;



}
