package com.baosight.payment.system.pojo.dao.tonglian;

import lombok.Data;

/**
 * 通联
 * @author longjiangran
 */
@Data
public class TongLianMemberBasicInfoDAO {
    /**
     * 会员类型
     * 2：企业会员
     * 3：个人会员
     */
    private String memberType;


    /**
     * 会员角色
     */
    private String memberRole;


    /**
     * 企业名称
     */
    private String enterpriseName;


}
