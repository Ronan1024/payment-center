package com.baosight.payment.system.pojo.entity.dynamicform;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 表单模式
 *
 * @author Dynamic Form Tool
 * @TableName form_schema
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "form_schema")
public class FormSchema extends BasePO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 表单名称
     */
    private String name;

    /**
     * 表单描述
     */
    private String description;

    /**
     * 场景标识符
     */
    private String scenarioContext;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;
}
