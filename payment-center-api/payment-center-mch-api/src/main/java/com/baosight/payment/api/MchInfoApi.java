package com.baosight.payment.api;

import com.baosight.payment.vo.MchInfoVO;

import java.util.List;

/**
 * @author L.J.Ran
 */
public interface MchInfoApi {

    /**
     * 获取商户信息
     */
    MchInfoVO mchInfo(Long mchId);

    /**
     * 获取商户信息
     */
    MchInfoVO mchInfoBuMchNO(String mchNo);

    /**
     * 查询服务商下的子账户
     * @param isvId
     * @return
     */
    List<Long> mchInfoByIsvId(Long isvId);
}
