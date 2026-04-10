package com.baosight.payment.system.pojo.dto.req;

import com.baosight.database.core.page.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付渠道流程分页请求DTO
 *
 * @author L.J.Ran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentChannelFlowPageReqDTO extends PageRequest {

    /**
     * 渠道编号
     */
    private String channelCode;

    /**
     * 渠道id
     */
    private Long channelId;
}
