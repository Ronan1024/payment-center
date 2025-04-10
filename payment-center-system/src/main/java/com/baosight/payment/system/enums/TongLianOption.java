package com.baosight.payment.system.enums;

import com.baosight.common.enums.IBaseEnum;
import com.baosight.payment.system.pojo.entity.PayTongLianRelevance;
import lombok.Getter;

import java.util.function.Function;

/**
 * @program: payment-center
 * @description: 通联操作流程code
 * @author: L.J.Ran
 * @create: 2025/3/25
 */
@Getter
public enum TongLianOption implements IBaseEnum<String> {

    /**
     * 绑定收银宝
     */
    BIND_SYB_MERCHANT_CODE("BIND_SYB", "绑定收银宝", 1, e -> e.getHasBindSyb().equals(Boolean.FALSE)),

    /**
     * 绑定手机号
     */
    BIND_PHONE("BIND_PHONE", "确认绑定手机号", 2, e -> e.getHasBindPhone().equals(Boolean.FALSE)),
    /**
     * 协议签约
     */
    PROTOCOL_SIGN("PROTOCOL_SIGN", "协议签约", 3, e -> e.getHasContractSign().equals(Boolean.FALSE)),
    ;

    /**
     * 执行流程
     */
    private final Integer sort;

    private final Function<PayTongLianRelevance, Boolean> function;

    TongLianOption(String code, String msg, Integer sort, Function<PayTongLianRelevance, Boolean> function) {
        initEnum(code, msg);
        this.sort = sort;
        this.function = function;
    }


}
