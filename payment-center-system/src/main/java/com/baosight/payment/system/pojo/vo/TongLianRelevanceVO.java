package com.baosight.payment.system.pojo.vo;

import lombok.Data;

@Data
public class TongLianRelevanceVO {

    /**
     * 是否绑定手机号
     */
    private Boolean hasBindPhone;

    /**
     * 是否绑定收银宝
     */
    private Boolean hasBindSyb;


    /**
     * 通联合约签订
     */
    private Boolean hasContractSign;
}
