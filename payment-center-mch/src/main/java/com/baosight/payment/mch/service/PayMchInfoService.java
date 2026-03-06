package com.baosight.payment.mch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.mch.dao.entity.PayMchInfo;
import com.baosight.payment.mch.pojo.dto.MchInfoDTO;
import com.baosight.payment.mch.pojo.dto.MchPageDTO;
import com.baosight.payment.mch.pojo.vo.PayMchInfoVO;
import com.baosight.payment.mch.pojo.vo.PayMchListVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_info(支付服务商信息表)】的数据库操作Service
 * @createDate 2025-03-17 13:13:10
 */
public interface PayMchInfoService extends IService<PayMchInfo> {

    /**
     * 获取商户列表
     *
     * @param mchPage 商户列表请求体
     */
    PageResponse<PayMchListVO> mchPage(MchPageDTO mchPage);

    /**
     * 创建商户信息
     *
     * @param mchInfoDTO 创建商户信息请求体
     */
    @Transactional
    Boolean createMch(MchInfoDTO mchInfoDTO);

    /**
     * 获取商户信息
     *
     * @param id 商户id
     */
    PayMchInfoVO info(Long id);

    /**
     * 更新商户信息
     *
     * @param id         商户ID
     * @param mchInfoDTO 商户信息请求体
     */
    @Transactional
    Boolean updateMch(Long id, MchInfoDTO mchInfoDTO);

    /**
     * 根据商户id 获取商户信息
     *
     * @param mchId 商户id
     */
    PayMchInfo infoById(Long mchId);

    /**
     * 获取商户信息
     *
     * @param mchNo 商户号
     */
    PayMchInfo infoByMchNo(String mchNo);

    /**
     * 根据租户ID获取租户相关的企业及法人信息
     * @param id
     * @return
     */
    PayMchInfoVO tenantMchInfo(Long id);
}
