package com.baosight.payment.mch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.core.page.PageResponse;
import com.baosight.database.core.page.PageUtil;
import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.enums.MchType;
import com.baosight.payment.isv.api.IsvInfoApi;
import com.baosight.payment.isv.vo.IsvInfoVO;
import com.baosight.payment.mapper.PayEnterpriseInfoMapper;
import com.baosight.payment.mch.convert.PayMchInfoConvert;
import com.baosight.payment.mch.error.MchError;
import com.baosight.payment.mch.mapper.PayBankAccountInfoMapper;
import com.baosight.payment.mch.mapper.PayMchInfoMapper;
import com.baosight.payment.mch.pojo.dto.MchInfoDTO;
import com.baosight.payment.mch.pojo.dto.MchPageDTO;
import com.baosight.payment.mch.pojo.entity.PayBankAccountInfo;
import com.baosight.payment.mch.pojo.entity.PayMchInfo;
import com.baosight.payment.mch.pojo.vo.PayMchInfoVO;
import com.baosight.payment.mch.pojo.vo.PayMchListVO;
import com.baosight.payment.mch.service.PayMchInfoService;
import com.baosight.payment.pojo.entity.PayEnterpriseInfo;
import com.baosight.payment.utils.IdGenUtil;
import com.baosight.saas.auth.context.UserContext;
import com.baosight.utils.utils.Assert;
import com.baosight.web.core.exception.ApiException;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_info(支付服务商信息表)】的数据库操作Service实现
 * @createDate 2025-03-17 13:13:10
 */
@Service
@RequiredArgsConstructor
public class PayMchInfoServiceImpl extends ServiceImpl<PayMchInfoMapper, PayMchInfo> implements PayMchInfoService {
    private final PayMchInfoMapper payMchInfoMapper;
    private final PayEnterpriseInfoMapper payEnterpriseInfoMapper;
    private final PayBankAccountInfoMapper payBankAccountInfoMapper;
//    @Resource
//    private TenantInfoApi tenantInfoApi;

    @Resource
    private IsvInfoApi isvInfoApi;

    /**
     * 获取商户列表
     *
     * @param mchPage 商户列表请求体
     */
    @Override
    public PageResponse<PayMchListVO> mchPage(MchPageDTO mchPage) {
        PageUtil<PayMchListVO> pageUtil = new PageUtil<>(mchPage);
        return pageUtil.builder(payMchInfoMapper.page(pageUtil.Page(), mchPage)).build();
    }

    /**
     * 创建商户信息
     *
     * @param mchInfoDTO 创建商户信息请求体
     */
    @Override
    public Boolean createMch(MchInfoDTO mchInfoDTO) {
        //处理特约商户
        handlerSpecialMch.accept(mchInfoDTO);
        // 按商户名称+联系人做唯一性判断
        PayMchInfo payMchInfo = payMchInfoMapper.selectOne(new LambdaQueryWrapper<PayMchInfo>()
                .eq(PayMchInfo::getEnterpriseName, mchInfoDTO.getMchName())
                .eq(PayMchInfo::getContactTel, mchInfoDTO.getContactTel()));
        Assert.notNull(payMchInfo, ApiException.supplier(MchError.MCH_INFO_EXIST));
        payMchInfo = PayMchInfoConvert.INSTANCE.toPayMchInfo(mchInfoDTO);
        PayEnterpriseInfo payEnterpriseInfo = PayMchInfoConvert.INSTANCE.toPayEnterpriseInfo(mchInfoDTO);
        payEnterpriseInfoMapper.insert(payEnterpriseInfo);
        PayBankAccountInfo payBankAccountInfo = PayMchInfoConvert.INSTANCE.toPayBankAccountInfo(mchInfoDTO);
        payBankAccountInfoMapper.insert(payBankAccountInfo);

        payMchInfo.setEnterpriseInfoId(payEnterpriseInfo.getId());
        payMchInfo.setBankAccountInfoId(payBankAccountInfo.getId());
        payMchInfo.setCreateBy(UserContext.INSTANCE.userId());
        payMchInfo.setCreateByName(UserContext.INSTANCE.username());
        if (mchInfoDTO.getType().equals(MchType.MERCHANT.code())) {
            payMchInfo.setContactName(mchInfoDTO.getRepresentativeName()); // 联系人，如果是特约商户，使用租户联系人；普通商户使用企业法人
        }
        String prefix = mchInfoDTO.getType().equals(MchType.MERCHANT.code()) ? "N" : "S";
        String mchNo = IdGenUtil.generateId(SnowflakeIdUtil.nextId());
        payMchInfo.setMchNo(prefix + mchNo);
        return payMchInfoMapper.insert(payMchInfo) > 0;
    }


    /**
     * 处理特约商户
     * 1、服务商ID不能为空
     * 2、服务商信息必须存在
     */
    private final Consumer<MchInfoDTO> handlerSpecialMch = (e) -> {
        if (e.getType().equals(MchType.SUB_MERCHANT.code())) {
            Assert.isNull(e.getIsvId(), ApiException.supplier(MchError.ISV_INFO_IS_NULL));
            IsvInfoVO isvInfoVO = isvInfoApi.isvInfoById(e.getIsvId());
            Assert.isNull(isvInfoVO, ApiException.supplier(MchError.ISV_INFO_IS_NULL));
        }
    };

    /**
     * 获取商户信息
     *
     * @param id 商户id
     */
    @Override
    public PayMchInfoVO info(Long id) {
        PayMchInfo payMchInfo = payMchInfoMapper.selectById(id);
        PayMchInfoVO payMchInfoVO = PayMchInfoConvert.INSTANCE.toPayMchInfoVO(payMchInfo);
        // 补充企业信息
        PayEnterpriseInfo payEnterpriseInfo = payEnterpriseInfoMapper.selectById(payMchInfo.getEnterpriseInfoId());
        PayMchInfoConvert.INSTANCE.toPayMchInfoVO(payEnterpriseInfo,payMchInfoVO);
        // 补充银行信息
        PayBankAccountInfo payBankAccountInfo = payBankAccountInfoMapper.selectById(payMchInfo.getBankAccountInfoId());
        PayMchInfoConvert.INSTANCE.toPayMchInfoVO(payBankAccountInfo,payMchInfoVO);

        return payMchInfoVO;
    }

    /**
     * 更新商户信息
     *
     * @param id         商户ID
     * @param mchInfoDTO 商户信息请求体
     */
    @Override
    public Boolean updateMch(Long id, MchInfoDTO mchInfoDTO) {
        PayMchInfo payMchInfo = infoById(id);
        Assert.isNull(payMchInfo, ApiException.supplier(MchError.MCH_INFO_NOT_FOUND));

        // 处理特约商户
        handlerSpecialMch.accept(mchInfoDTO);

        // 更新企业信息
        PayEnterpriseInfo payEnterpriseInfo = PayMchInfoConvert.INSTANCE.toPayEnterpriseInfo(mchInfoDTO);
        payEnterpriseInfo.setId(payMchInfo.getEnterpriseInfoId());
        payEnterpriseInfoMapper.updateById(payEnterpriseInfo);
        // 更新银行信息
        PayBankAccountInfo payBankAccountInfo = PayMchInfoConvert.INSTANCE.toPayBankAccountInfo(mchInfoDTO);
        payBankAccountInfo.setId(payMchInfo.getBankAccountInfoId());
        payBankAccountInfoMapper.updateById(payBankAccountInfo);
        // 更新商户基本信息
        PayMchInfoConvert.INSTANCE.copyPayMchInfo(mchInfoDTO, payMchInfo);
        // 联系人，如果是特约商户，使用租户联系人；普通商户使用企业法人
        if (mchInfoDTO.getType().equals(MchType.MERCHANT.code())) {
            payMchInfo.setContactName(mchInfoDTO.getRepresentativeName());
        }
        payMchInfo.setUpdateBy(UserContext.INSTANCE.userId());
        payMchInfo.setUpdateByName(UserContext.INSTANCE.username());
        return payMchInfoMapper.updateById(payMchInfo) > 0;
    }

    /**
     * 根据商户id 获取商户信息
     *
     * @param mchId 商户id
     */
    @Override
    public PayMchInfo infoById(Long mchId) {
        return payMchInfoMapper.selectById(mchId);
    }

    /**
     * 获取商户信息
     *
     * @param mchNo 商户号
     */
    @Override
    public PayMchInfo infoByMchNo(String mchNo) {
        return payMchInfoMapper.selectOne(new LambdaQueryWrapper<PayMchInfo>()
                .eq(PayMchInfo::getMchNo, mchNo)
        );
    }

    @Override
    public PayMchInfoVO tenantMchInfo(Long id) {
//        TenantDetailInfoVO tenantDetailInfo = tenantInfoApi.getTenantDetailInfo(id);
//        PayMchInfoVO payMchInfoVO = PayMchInfoConvert.INSTANCE.toPayMchInfoVO(tenantDetailInfo);
//        if (tenantDetailInfo.getParentId() != null && tenantDetailInfo.getParentId() != 0) {
//            payMchInfoVO.setType(MchType.SUB_MERCHANT.code());
//        } else {
//            payMchInfoVO.setType(MchType.MERCHANT.code());
//        }
//
//        return payMchInfoVO;
        return null;
    }
}




