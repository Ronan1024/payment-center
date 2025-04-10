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
public class CheckBatchPageDTO extends PageRequest {

    /**
     * 批次编号
     */
    private String checkBatchCode;



}
