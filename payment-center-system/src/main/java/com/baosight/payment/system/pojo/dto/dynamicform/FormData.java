package com.baosight.payment.system.pojo.dto.dynamicform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 表单数据
 *
 * @author Dynamic Form Tool
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字段值映射 (字段名 -> 字段值)
     */
    private Map<String, Object> fieldValues;

    /**
     * 获取字段值
     */
    public Object getFieldValue(String fieldName) {
        if (fieldValues == null) {
            return null;
        }
        return fieldValues.get(fieldName);
    }

    /**
     * 设置字段值
     */
    public void setFieldValue(String fieldName, Object value) {
        if (fieldValues == null) {
            fieldValues = new HashMap<>();
        }
        fieldValues.put(fieldName, value);
    }

    /**
     * 检查字段是否存在
     */
    public boolean hasField(String fieldName) {
        return fieldValues != null && fieldValues.containsKey(fieldName);
    }
}
