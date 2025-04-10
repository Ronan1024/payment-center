package com.baosight.payment.api;

import com.baosight.payment.dao.TongLianConfigVO;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.vo.MchAppConfigInfoVO;
import com.baosight.payment.vo.MchAppInfoVO;
import com.baosight.payment.vo.MchInterfaceConfigVO;

import java.util.List;

public interface MchAppConfigApi {
    /**
     * 获取应用信息
     */
    MchAppInfoVO mchApiInfo(Long mchNo, String appNo);

    /**
     *
     */
    MchAppConfigInfoVO McAppConfigInfo(Long mchNo, Long AppId);

    /**
     * 获取通联配置信息
     */
    TongLianConfigVO tongLianConfig(Long mchNo);

    /**
     * 获取通联支付配置
     *
     * @param mchId         商户id
     * @param interfaceCode 接口编号
     * @param isvId         服务商id
     */
    TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfig(Long mchId, String interfaceCode, Long isvId);

    /**
     * 获取所有服务商的配置信息
     */
    List<MchInterfaceConfigVO> isvConfig(String interfaceCode);


    /**
     * 获取下级子商户的配置信息
     * @param isvId 服务商id
     * @param interfaceCode 支付接口编号
     */
    List<MchInterfaceConfigVO> mchConfig(Long isvId, String interfaceCode);
}
