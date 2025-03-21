package com.baosight.payment.system.service;

import com.baosight.payment.system.pojo.dto.CreateAppDTO;
import com.baosight.payment.system.pojo.dto.MchAppListDTO;
import com.baosight.payment.system.pojo.entity.PayMchApp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.system.pojo.vo.MchAppListVO;
import com.baosight.payment.system.pojo.vo.MchPayAppInfoVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_app(商户应用表)】的数据库操作Service
 * @createDate 2025-02-24 16:58:18
 */
public interface PayMchAppService extends IService<PayMchApp> {

    /**
     * 创建或者更新支付应用信息
     *
     * @param createAppDTO 创建应用id
     * @param mchId        商户id
     */
    @Transactional(rollbackFor = Exception.class)
    Long createOrUpdate(CreateAppDTO createAppDTO, Long mchId);

    /**
     * saas 获取商户应用配置详情 临时使用后期进行删除剥离
     *
     * @param appId saas应用id
     */
    MchPayAppInfoVO appInfoBySaasAppId(Integer appId);

    /**
     * 获取应用信息
     *
     * @param appId 应用id
     */
    MchPayAppInfoVO appInfo(Long appId);

    /**
     * saas 创建商户应用
     *
     * @param create 创建应用请求体
     * @param appId  appId
     */
    Boolean saasCreateOrUpdate(CreateAppDTO create, Integer appId);

    /**
     * 获取支付商户应用信息
     *
     * @param mchId 商户ID
     * @param appId 应用ID
     */
    PayMchApp appInfo(Long mchId, Long appId);

    /**
     * 获取商家应用数据
     *
     * @param mchId         商家id
     * @param mchAppListDTO 获取商家应用列表请求数据
     */
    List<MchAppListVO> machAppList(Long mchId, MchAppListDTO mchAppListDTO);

    /**
     * 获取商户应用详情
     *
     * @param id 商户应用id
     */
    MchPayAppInfoVO info(Long id);
}
