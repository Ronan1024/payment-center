package com.baosight.payment.model;

import lombok.Data;

/**
 * 签约表单信息
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/15
 */
@Data
public class SignForm {

    /**
     * 字段
     */
    private String fieldKey;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;


    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 是否加密存储
     */
    private Boolean encrypted;

    /**
     * 输入提示
     */
    private String placeholder;

    /**
     * 是否敏感字段，敏感字段页面应脱敏展示
     */
    private Boolean sensitive;

    /**
     * 帮助说明
     */
    private String helpText;

    /**
     * 默认值
     */
    private String defaultVal;


    /**
     * 排序
     */
    private Integer sortNo;

}
