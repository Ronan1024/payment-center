package com.baosight.payment.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字段类型枚举
 *
 * @author Dynamic Form Tool
 */
@Getter
@AllArgsConstructor
public enum FieldType {
    TEXT("text", "文本"),
    NUMBER("number", "数字"),
    DATE("date", "日期"),
    BOOLEAN("boolean", "布尔值"),
    RADIO("radio", "单选"),
    CHECKBOX("checkbox", "多选"),
    FILE("file", "文件上传");

    private final String code;
    private final String description;

    public static FieldType fromCode(String code) {
        for (FieldType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown field type: " + code);
    }
}
