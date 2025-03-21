package com.baosight.payment.system.pojo.dao.tonglian;


import lombok.Data;

@Data
public class TongLianMchConfigDAO {

    /**
     * 收银宝商户号
     */
    private String signNum;

    /**
     * 商户名称
     */
    private String signName;
}
