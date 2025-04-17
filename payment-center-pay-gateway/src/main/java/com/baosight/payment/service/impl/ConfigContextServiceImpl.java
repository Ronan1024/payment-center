package com.baosight.payment.service.impl;

import com.baosight.payment.pojo.dao.MchAppConfigInfoDAO;
import com.baosight.payment.service.ConfigContextService;
import org.springframework.stereotype.Service;


@Service
public class ConfigContextServiceImpl implements ConfigContextService {

    /**
     * 是否启用缓存
     * true: 表示将使用内存缓存， 将部分系统配置项 或 商户应用/服务商信息进行缓存并读取
     * false: 直接查询DB
     **/
    public static boolean IS_USE_CACHE = false;

    /**
     * 获取商户信息以及配置信息
     *
     * @param mchId
     * @param mchAppId
     */
    @Override
    public MchAppConfigInfoDAO mchAppConfigInfo(String mchId, String mchAppId) {
        if (IS_USE_CACHE) {

        }
        // 获取商户信息

        return null;
    }
}
