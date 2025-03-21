package com.baosight.payment.system.pojo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TongLianAgreementDTO {

    /**
     * 平台抽佣比例
     * 比例：单位 %，精确到小数点后2位
     */
    @NotNull(message = "平台抽取比例不能为空")
    private Long couponRate;
}
