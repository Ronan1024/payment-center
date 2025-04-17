package com.baosight.payment.service;

import com.baosight.payment.pojo.dao.MchAppConfigInfoDAO;

public interface ConfigContextService {

    /**
     * 获取商户信息以及配置信息
     */
    MchAppConfigInfoDAO  mchAppConfigInfo(String mchId, String mchAppId);
}
