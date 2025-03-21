package com.baosight.payment.api;

import com.baosight.payment.dao.TongLianConfigVO;
import com.baosight.payment.vo.MchAppConfigInfoVO;
import com.baosight.payment.vo.MchAppInfoVO;

public interface MchAppConfigApi {
    /**
     * 获取应用信息
     */
    MchAppInfoVO mchApiInfo(Long mchNo, Long AppId);

    /**
     *
     */
    MchAppConfigInfoVO McAppConfigInfo(Long mchNo, Long AppId);

    /**
     * 获取通联配置信息
     */
    TongLianConfigVO tongLianConfig(Long mchNo);
}
