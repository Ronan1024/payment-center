package com.baosight.payment.system.utils;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
public class DynamicFormUtil {


    /**
     * 动态表单
     */
    @Data
    public static class DynamicForm {
        /**
         * 表单名
         */
        @NotBlank(message = "字段名不能为空")
        private String name;


        /**
         * 排序
         */
        private Integer sort;

        /**
         * 表单值
         */
        private String value;

        /**
         * 字段类型
         */
        @NotBlank(message = "字段类型不能为空")
        private String type;

        /**
         * 是否必须
         */
        @NotBlank(message = "是否必填不能为空")
        private Boolean required;

        /**
         * 字段描述
         */
        private String desc;
    }

}
