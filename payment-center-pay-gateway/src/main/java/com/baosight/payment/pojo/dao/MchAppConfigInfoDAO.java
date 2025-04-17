package com.baosight.payment.pojo.dao;

import com.baosight.payment.dao.TongLianConfigVO;
import lombok.Data;

@Data
public class MchAppConfigInfoDAO {
    /**
     * 商户id
     */
    private String mchId;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 商户类型
     */
    private String mchType;

    /**
     * 商户名称
     */
    private String mchName;

    /**
     * 商户简称
     */
    private String mchShortName;

    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人手机号
     */
    private String contactTel;

    /**
     * 商户状态
     */
    private Integer state;

    private TongLianConfigVO tongLianConfigDAO;
}
