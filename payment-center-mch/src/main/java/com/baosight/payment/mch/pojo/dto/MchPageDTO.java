package com.baosight.payment.mch.pojo.dto;


import com.baosight.database.core.page.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: payment-center
 * @description: 商户分页查询DTO
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MchPageDTO extends PageRequest {

    /**
     * 商户编号
     */
    private String mchNo;

    /**
     * 企业名称
     */
    private String mchName;

    /**
     * 商户类型（2.普通商户 3.特约商户）
     */
    private Integer type;

    /**
     * 联系人电话
     */
    private String contactTel;

    /**
     * 服务商编号
     */
    private String isvCode;

    /**
     * 服务商名称
     */
    private String isvName;
}
