package com.baosight.payment.api;

import com.baosight.payment.vo.MchInfoVO;

/**
 * @author L.J.Ran
 */
public interface MchInfoApi {

    /**
     * 获取商户信息
     */
    MchInfoVO mchInfo(Long mchId);
}
