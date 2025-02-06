package com.baosight.payment.pojo.dto;

import com.baosight.database.page.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PayWayPageDTO extends PageRequest {
    /**
     * 支付方式code
     */
    private String payWayCode;

    /**
     * 支付名称
     */
    private String payWayName;
}
