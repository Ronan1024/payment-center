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
     * 商户id
     */
    private Long mchId;

    /**
     * 商户名称
     */
    private String name;

    /**
     * 服务商号
     */
    private Long isvId;

    /**
     * 商户类型
     */
    private Integer type;

}
