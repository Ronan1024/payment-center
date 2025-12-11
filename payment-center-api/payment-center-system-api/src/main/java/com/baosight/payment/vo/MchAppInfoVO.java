package com.baosight.payment.vo;


import com.baosight.web.core.serializer.CustomLongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * @author L.J.Ran
 */
@Data
public class MchAppInfoVO {
    /**
     * 应用ID
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long id;

    /**
     * 应用编号
     */
    private String appCode;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 商户号
     */
    @JsonSerialize(using = CustomLongSerializer.class)
    private Long mchId;

    /**
     * 应用状态: 0-停用, 1-正常
     */
    private Integer state;

    /**
     * 应用私钥
     */
    private String appSecret;

    /**
     * 备注
     */
    private String remark;
}
