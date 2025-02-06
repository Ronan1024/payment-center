package com.baosight.payment.pojo.dto;

import com.baosight.database.page.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class IsvPageDTO extends PageRequest {

    /**
     * 服务商名称
     */
    private String name;

    /**
     * 服务商id
     */
    private Long id;

}
