package com.baosight.payment.pojo.dto;

import lombok.Data;
import com.baosight.database.core.page.PageRequest;

/**
 * <p>
 * 接口类型配置表
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2025-12-16
 */
@Data
public class PayInterfaceTypePageDTO extends PageRequest {

    /**
     * 接口类型代码
     */
    private String interfaceTypeCode;

    /**
     * 接口类型名称
     */
    private String interfaceTypeName;

}
