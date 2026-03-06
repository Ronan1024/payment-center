package com.baosight.payment.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证规则类型枚举
 *
 * @author Dynamic Form Tool
 */
@Getter
@AllArgsConstructor
public enum ValidationType {
    REQUIRED("required", "必填"),
    MIN_LENGTH("minLength", "最小长度"),
    MAX_LENGTH("maxLength", "最大长度"),
    REGEX("regex", "正则表达式"),
    MIN_VALUE("minValue", "最小值"),
    MAX_VALUE("maxValue", "最大值"),
    MIN_DATE("minDate", "最小日期"),
    MAX_DATE("maxDate", "最大日期");

    private final String code;
    private final String description;

    public static ValidationType fromCode(String code) {
        for (ValidationType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown validation type: " + code);
    }
}
