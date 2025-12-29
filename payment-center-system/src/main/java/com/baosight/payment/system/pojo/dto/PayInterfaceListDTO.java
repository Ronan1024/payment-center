package com.baosight.payment.system.pojo.dto;

import com.baosight.database.core.page.PageRequest;
import lombok.Data;

/**
 * @program: payment-center
 * @description: 服务商接口列表查询请求体
 * @author: L.J.Ran
 * @create: 2025/3/17$
 **/
@Data
public class PayInterfaceListDTO extends PageRequest {
    /**
     * 支付接口代码
     */
    private String code;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口类型名称
     */
    private String interfaceTypeName;
    /**
     * 支付方式名称
     */
    private String payName;

    /**
     * 应用场景
     */
    private Integer scenario;


}
