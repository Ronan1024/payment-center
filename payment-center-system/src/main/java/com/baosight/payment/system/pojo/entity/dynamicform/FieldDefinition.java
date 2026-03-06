package com.baosight.payment.system.pojo.entity.dynamicform;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字段定义
 *
 * @author Dynamic Form Tool
 * @TableName field_definition
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "field_definition")
public class FieldDefinition extends BasePO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 表单模式ID
     */
    private Long formSchemaId;

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
     * 字段标签
     */
    private String label;

    /**
     * 占位符
     */
    private String placeholder;

    /**
     * 提示信息
     */
    private String hint;

    /**
     * 排序顺序
     */
    private Integer sortOrder;

    /**
     * 显示条件
     */
    private String displayCondition;
}
