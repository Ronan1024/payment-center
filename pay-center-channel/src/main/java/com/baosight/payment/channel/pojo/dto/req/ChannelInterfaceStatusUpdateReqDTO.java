package com.baosight.payment.channel.pojo.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 渠道接口状态修改请求。
 *
 * <p>
 * 仅允许修改渠道接口的平台状态，不承载接口编码、名称、能力等其它配置字段，
 * 避免状态操作误覆盖接口基础信息。
 * </p>
 */
@Data
public class ChannelInterfaceStatusUpdateReqDTO {

    /**
     * 渠道接口 ID
     */
    @NotNull(message = "渠道接口ID不能为空")
    private Long id;

    /**
     * 接口状态，见 InterfaceStatusEnum
     */
    @NotNull(message = "接口状态不能为空")
    private Integer status;


    /**
     * 禁用备注
     */
    private String remark;
}
