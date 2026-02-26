package com.baosight.payment.system.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PayInterfaceDefineBindDTO {
    private Long mchId;
    private Integer payClientType;
    private List<Long> payInterfaceIdList;
}
