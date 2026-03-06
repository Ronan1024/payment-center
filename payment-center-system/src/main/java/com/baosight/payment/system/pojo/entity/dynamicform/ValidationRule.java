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
 * 验证规则
 *
 * @author Dynamic Form Tool
 * @TableName validation_rule
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "validation_rule")
public class ValidationRule extends BasePO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 字段定义ID
     */
    private Long fieldDefinitionId;

    /**
     * 验证规则类型
     */
    private String ruleType;

    /**
     * 规则参数（JSON格式）
     */
    private String ruleParams;

    /**
     * 错误消息
     */
    private String errorMessage;
}
