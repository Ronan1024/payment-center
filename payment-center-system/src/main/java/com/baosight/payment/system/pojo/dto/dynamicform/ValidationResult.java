package com.baosight.payment.system.pojo.dto.dynamicform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 验证结果
 *
 * @author Dynamic Form Tool
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 是否验证通过
     */
    private Boolean valid;

    /**
     * 错误信息列表
     */
    private List<FieldError> errors;

    /**
     * 字段错误信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldError implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 字段名称
         */
        private String fieldName;

        /**
         * 错误消息
         */
        private String errorMessage;
    }

    /**
     * 创建成功的验证结果
     */
    public static ValidationResult success() {
        return new ValidationResult(true, new ArrayList<>());
    }

    /**
     * 创建失败的验证结果
     */
    public static ValidationResult failure(List<FieldError> errors) {
        return new ValidationResult(false, errors);
    }

    /**
     * 添加错误信息
     */
    public void addError(String fieldName, String errorMessage) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(new FieldError(fieldName, errorMessage));
        this.valid = false;
    }
}
