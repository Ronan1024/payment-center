package com.baosight.payment.adapter.enums;

/**
 * 适配器调用操作，用于映射运行控制影响范围。
 */
public enum AdapterOperation {
    PAY(AdapterEffectScope.PAY),
    QUERY(AdapterEffectScope.QUERY),
    CLOSE(AdapterEffectScope.CLOSE),
    REFUND(AdapterEffectScope.REFUND),
    REFUND_QUERY(AdapterEffectScope.REFUND_QUERY),
    NOTIFY(AdapterEffectScope.NOTIFY),
    BILL(AdapterEffectScope.BILL);

    private final AdapterEffectScope effectScope;

    AdapterOperation(AdapterEffectScope effectScope) {
        this.effectScope = effectScope;
    }

    public AdapterEffectScope effectScope() {
        return effectScope;
    }
}
