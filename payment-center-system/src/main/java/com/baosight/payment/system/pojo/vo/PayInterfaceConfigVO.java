package com.baosight.payment.system.pojo.vo;

import com.baosight.database.mybatis.handler.BasePO;
import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class PayInterfaceConfigVO extends BasePO {
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

//    @JsonSerialize(using = CustomLongSerializer.class)
//    private Long id;
//
//    /**
//     * 客户端类型
//     */
//    private Integer clientType;
//
//    private String name;
//
//    @JsonSerialize(using = CustomLongSerializer.class)
//    private Long clientId;
//
//    /**
//     * 支付接口id
//     */
//    @JsonSerialize(using = CustomLongSerializer.class)
//    private Long interfaceId;
//
//    /**
//     * 支付接口参数
//     */
//    private String interfaceParams;
//
//    /**
//     * 支付接口参数obj
//     */
////    private List<DynamicForm> interfaceParam;
//
//    /**
//     * 支付接口费率
//     */
//    private Long interfaceRate;
//
//    /**
//     * 是否启用
//     */
//    private Boolean enable;
//
//    /**
//     * 备注信息
//     */
//    private String remark;
//
//    private Long createBy;
//
//    private Long updateBy;
//
//    /**
//     * 支付机构
//     */
//    private String payingAgency;
//
//    /**
//     * 是否配置
//     */
//    @JsonIgnore
//    private Boolean hasSetting;
//
//    /**
//     * 服务商id
//     */
//    @JsonIgnore
//    private Long isvId;
}
