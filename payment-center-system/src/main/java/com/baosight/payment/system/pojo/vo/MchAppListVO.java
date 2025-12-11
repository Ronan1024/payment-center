package com.baosight.payment.system.pojo.vo;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: payment-center
 * @description: 商家app列表
 * @author: L.J.Ran
 * @create: 2025/3/19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MchAppListVO extends BasePO {
    /**
     * 应用ID
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用状态:
     */
    private Integer state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建者用户ID
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long createBy;

    /**
     * 创建者姓名
     */
    private String createdByName;

    /**
     * 应用编号
     */
    private String appCode;

    private List<String> payWay;
}
