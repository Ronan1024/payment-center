package com.baosight.payment.system.pojo.vo;

import com.baosight.database.mybatis.handler.BasePO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author L.J.Ran
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayInterfaceConfigDynamicVO extends BasePO {
    /**
     * 主键ID（pay_interface_config表的主键）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 支付名称（如微信支付、支付宝）
     */
    private String name;

    /**
     * key：标签名称（英文）
     * value:标签取值
     */
    private List<ParamVo> paramList;

    /**
     * 启用状态
     */
    private Boolean enable;


    @Data
    @Accessors(chain = true)
    public static class ParamVo {
        /**
         * 英文字段名
         */
        private String nameEn;
        /**
         * 中文字段名
         */
        private String name;
        /**
         * 字段排序
         */
        private Integer sort;
        /**
         * 字段取值
         */
        private Object value;
        /**
         * 是否必填
         */
        private Boolean required;
    }

}
