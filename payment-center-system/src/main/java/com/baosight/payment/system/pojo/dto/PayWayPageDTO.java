package com.baosight.payment.system.pojo.dto;

import com.baosight.database.core.page.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author L.J.Ran
 */
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

    /**
     * 支付类别
     */
    private Integer payingCategory;

    /**
     * 使用状态
     */
    private Boolean disable;

}
