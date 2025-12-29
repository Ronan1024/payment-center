package com.baosight.payment.system.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baosight.database.mybatis.handler.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 接口类型配置表
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2025-12-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pay_interface_type")
public class PayInterfaceTypeVO extends BasePO {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 接口类型代码
     */
    private String interfaceTypeCode;

    /**
     * 接口类型名称
     */
    private String interfaceTypeName;

    /**
     * 是否禁用
     */
    private Boolean disable;

    /**
     * 支持的配置方式
     */
    private String configWay;

    /**
     * 备注
     */
    private String remark;

    /**
     * 配置定义描述
     */
    private String description;

    /**
     * 回调白名单
     */
    private String callbackIp;

    /**
     * 是否开启回调
     */
    private Boolean isCallback;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;


}
