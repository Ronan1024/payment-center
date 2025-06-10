package com.baosight.payment.api;


import com.baosight.payment.vo.IsvInterfaceConfigVO;
import com.baosight.payment.vo.MchInterfaceConfigVO;
import com.baosight.payment.vo.PayInterfaceVO;

import java.util.List;

public interface PayInterfaceApi {

    List<PayInterfaceVO> interfaceDefineAll(List<Long> interfaceId);

    /**
     * 根据接口code 以及 渠道用户信息获取接口配置信息
     *
     * @param interfaceCode  接口编号
     * @param mchChannelUser 渠道用户信息
     */
    MchInterfaceConfigVO mchInterfaceConfig(String interfaceCode, String mchChannelUser);

    /**
     * 根据接口code 以及 商户渠道用户信息获取接口配置信息
     *
     * @param interfaceCode  接口编号
     * @param mchChannelUser 渠道用户信息
     */
    IsvInterfaceConfigVO isvInterfaceConfig(String interfaceCode, String mchChannelUser);
}
