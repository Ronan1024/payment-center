package com.baosight.payment.channel.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * 支付渠道信息 DTO
 *
 * @author L.J.Ran
 * @since 2026-03-04
 */
@Data
public class ChannelInfoDTO {

    /**
     * 渠道ID（更新时必填）
     */
    private Long id;

    /**
     * 渠道编号
     */
    @NotBlank(message = "渠道编号不能为空")
    private String channelCode;

    /**
     * 渠道机构
     */
    private String channelAgency;

    /**
     * 渠道类型
     */
    @NotNull(message = "渠道类型不能为空")
    private Integer type;
}
