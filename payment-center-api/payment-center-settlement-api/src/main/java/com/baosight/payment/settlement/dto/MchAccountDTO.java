package com.baosight.payment.settlement.dto;

import cn.hutool.core.math.Money;
import lombok.Data;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/21
 */
@Data
public class MchAccountDTO {
    private Long mchId;

    private Integer type;

    private List<Money> amount;
}
