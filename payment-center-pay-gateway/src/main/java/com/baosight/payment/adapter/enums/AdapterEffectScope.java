package com.baosight.payment.adapter.enums;

import lombok.Getter;

/**
 * 适配器运行控制影响范围。
 */
@Getter
public enum AdapterEffectScope {
    ALL("全部能力"),
    PAY("新支付"),
    QUERY("查单"),
    CLOSE("关单"),
    REFUND("新退款"),
    REFUND_QUERY("退款查询"),
    NOTIFY("回调解析"),
    BILL("账单下载");

    private final String desc;

    AdapterEffectScope(String desc) {
        this.desc = desc;
    }
}
