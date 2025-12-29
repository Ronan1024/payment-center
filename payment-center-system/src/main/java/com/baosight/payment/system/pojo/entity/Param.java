package com.baosight.payment.system.pojo.entity;

import lombok.Data;

/**
 * 参数结构定义
 */
@Data
public class Param {
    /**
     * 字段名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 字段中文名
     */
    private String description;
}
