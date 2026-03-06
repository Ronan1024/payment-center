package com.baosight.payment.channel.pojo.dto;

import com.baosight.database.core.page.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付渠道信息分页查询 DTO
 *
 * @author L.J.Ran
 * @since 2026-03-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayingChannelInfoPageDTO extends PageRequest {

    /**
     * 渠道名称（模糊查询）
     */
    private String channelName;

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 渠道类型
     */
    private Integer type;
}
