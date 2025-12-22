package com.baosight.payment.pojo.dto;

import com.baosight.database.core.page.PageRequest;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 支付类型表
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2025-12-18
 */
@Data
public class PayTypePageDTO extends PageRequest {



    private Long id;

    /**
     * 支付类型代码
     */
    private String payTypeCode;

    /**
     * 支付类型名称
     */
    private String payTypeName;

    /**
     * 支付类别
     */
    private Boolean payingCategory;

    /**
     * 支付机构代码
     */
    private String payingAgency;

    /**
     * 支付可用客户端
     */
    private Boolean payingClient;

    /**
     * 是否禁用
     */
    private Boolean disable;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 更新人
     */
    private Long updateBy;


}
