package com.baosight.payment.system.pojo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 参数结构定义
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties
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

    // 核心：通过 @JsonCreator 标记构造器，@JsonProperty(required = true) 强制生效
    @JsonCreator
    public Param(
            @JsonProperty(value = "name", required = true) String name, // 强制必填
            @JsonProperty(value = "sort", required = true) Integer sort,
            @JsonProperty(value = "required", required = true) Boolean required,
            @JsonProperty(value = "description", required = true) String description
    ) {
        this.name = name;
        this.sort = sort;
        this.required = required;
        this.description = description;
    }
}
