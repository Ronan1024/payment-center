package com.baosight.payment.mch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.core.page.PageResponse;
import com.baosight.database.core.page.PageUtil;
import com.baosight.payment.enums.MchType;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.isv.api.IsvInfoApi;
import com.baosight.payment.isv.vo.IsvInfoVO;
import com.baosight.payment.mapper.PayEnterpriseInfoMapper;
import com.baosight.payment.mch.convert.PayMchInfoConvert;
import com.baosight.payment.mch.dao.entity.PayMchInfo;
import com.baosight.payment.mch.dao.manager.MchInfoManager;
import com.baosight.payment.mch.dao.mapper.PayMchInfoMapper;
import com.baosight.payment.mch.error.MchError;
import com.baosight.payment.mch.mapper.PayBankAccountInfoMapper;
import com.baosight.payment.mch.pojo.dto.MchInfoDTO;
import com.baosight.payment.mch.pojo.dto.MchPageDTO;
import com.baosight.payment.mch.pojo.entity.PayBankAccountInfo;
import com.baosight.payment.mch.pojo.vo.PayMchInfoVO;
import com.baosight.payment.mch.pojo.vo.PayMchListVO;
import com.baosight.payment.mch.service.PayMchInfoService;
import com.baosight.payment.pojo.entity.PayEnterpriseInfo;
import com.baosight.payment.utils.CodeUtil;
import com.baosight.saas.auth.context.UserContext;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.utils.Assert;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.baosight.payment.mch.constant.RedisConstant.MCH_ID_SEQUENCE;
import static com.baosight.payment.mch.constant.RedissonConstant.CREATE_MCH_USER;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_info(支付服务商信息表)】的数据库操作Service实现
 * @createDate 2025-03-17 13:13:10
 */
@Service
@RequiredArgsConstructor
public class PayMchInfoServiceImpl extends ServiceImpl<PayMchInfoMapper, PayMchInfo> implements PayMchInfoService {
    private final PayMchInfoMapper payMchInfoMapper;
    private final MchInfoManager mchInfoManager;
    private final PayEnterpriseInfoMapper payEnterpriseInfoMapper;
    private final PayBankAccountInfoMapper payBankAccountInfoMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

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
        PageResponse<PayMchListVO> build = pageUtil.builder(payMchInfoMapper.page(pageUtil.Page(), mchPage)).build();
        List<PayMchListVO> list = build.getList();
        if (CollectionUtils.isEmpty(list)) {
            return build;
        }
        List<Long> isvIdList = list.stream().filter(e -> e.getType().equals(MchType.SUB_MERCHANT.code())).map(PayMchListVO::getIsvId).toList();
        List<IsvInfoVO> isvInfoVOS = isvInfoApi.isvInfoList(isvIdList);
        if (!CollectionUtils.isEmpty(isvInfoVOS)) {
            Map<Long, IsvInfoVO> isvInfoVOMap = isvInfoVOS.stream().collect(Collectors.toMap(IsvInfoVO::getId, e -> e));
            list.stream().filter(e -> e.getType().equals(MchType.SUB_MERCHANT.code()))
                    .filter(e -> isvInfoVOMap.containsKey(e.getIsvId()))
                    .forEach(e -> {
                        IsvInfoVO isvInfoVO = isvInfoVOMap.get(e.getIsvId());
                        e.setIsvId(isvInfoVO.getId());
                        e.setIsvName(isvInfoVO.getName());
                        e.setIsvCode(isvInfoVO.getCode());
                    });
        }


        return build;
    }

    /**
     * 创建商户信息
     *
     * @param mchInfoDTO 创建商户信息请求体
     * @param clientType
     */
    @Override
    public Boolean createMch(MchInfoDTO mchInfoDTO, PayClientType clientType) {
        //处理特约商户
        handlerSpecialMch.accept(mchInfoDTO);
        // 按商户名称+联系人做唯一性判断
        PayMchInfo payMchInfo = payMchInfoMapper.selectOne(new LambdaQueryWrapper<PayMchInfo>()
                .eq(PayMchInfo::getEnterpriseName, mchInfoDTO.getMchName())
                .eq(PayMchInfo::getContactTel, mchInfoDTO.getContactTel()));
        Assert.notNull(payMchInfo, ApiException.supplier(MchError.MCH_INFO_EXIST));
        payMchInfo = PayMchInfoConvert.INSTANCE.toPayMchInfo(mchInfoDTO);
        payMchInfo.setCreateBy(UserContext.INSTANCE.userId());
        payMchInfo.setCreateByName(UserContext.INSTANCE.username());
        if (mchInfoDTO.getType().equals(MchType.MERCHANT.code())) {
            // 联系人，如果是特约商户，使用租户联系人；普通商户使用企业法人
            payMchInfo.setContactName(mchInfoDTO.getRepresentativeName());
        }
        genMchCode(payMchInfo, clientType);

        PayEnterpriseInfo payEnterpriseInfo = PayMchInfoConvert.INSTANCE.toPayEnterpriseInfo(mchInfoDTO);
        PayBankAccountInfo payBankAccountInfo = PayMchInfoConvert.INSTANCE.toPayBankAccountInfo(mchInfoDTO);

        return mchInfoManager.createMchInfo(payMchInfo, payEnterpriseInfo, payBankAccountInfo);
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
        PayMchInfoConvert.INSTANCE.toPayMchInfoVO(payEnterpriseInfo, payMchInfoVO);
        // 补充银行信息
        PayBankAccountInfo payBankAccountInfo = payBankAccountInfoMapper.selectById(payMchInfo.getBankAccountInfoId());
        PayMchInfoConvert.INSTANCE.toPayMchInfoVO(payBankAccountInfo, payMchInfoVO);
        return payMchInfoVO;
    }

    /**
     * 更新商户信息
     *
     * @param id         商户ID
     * @param mchInfoDTO 商户信息请求体
     * @param clientType 操作人员客户端类型
     */
    @Override
    public Boolean updateMch(Long id, MchInfoDTO mchInfoDTO, PayClientType clientType) {
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

        if (!StringUtils.hasText(payMchInfo.getMchNo()) || !StringUtils.hasText(payMchInfo.getIsvCode())) {
            genMchCode(payMchInfo, clientType);
        }
        return payMchInfoMapper.updateById(payMchInfo) > 0;
    }


    /**
     * 生成商户编号
     *
     * @param payMchInfo 商户信息
     * @param clientType 操作客户端类型
     */
    private void genMchCode(PayMchInfo payMchInfo, PayClientType clientType) {
        if (payMchInfo.getType().equals(PayClientType.SUB_MERCHANT.code()) && !StringUtils.hasText(payMchInfo.getIsvCode())) {
            IsvInfoVO isvInfoVO = isvInfoApi.isvInfoById(payMchInfo.getIsvId());
            payMchInfo.setIsvCode(isvInfoVO.getCode());
        }
        if (StringUtils.hasText(payMchInfo.getMchNo())) {
            return;
        }
        RLock lock = redissonClient.getLock(CREATE_MCH_USER);
        lock.lock();
        try {
            // 有效期23:59:59
            Long increment = redisTemplate.opsForValue().increment(MCH_ID_SEQUENCE);
            assert increment != null;
            if (increment.equals(1L)) {
                LocalDateTime now = LocalDateTime.now();
                LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
                long seconds = Duration.between(now, endOfDay).getSeconds();
                redisTemplate.expire(MCH_ID_SEQUENCE, seconds, TimeUnit.SECONDS);
            }
            String sequence = String.format("%04d", increment);
            String mchCode;
            if (payMchInfo.getType().equals(MchType.MERCHANT.code())) {
                mchCode = CodeUtil.merchantCode(clientType, sequence);
            } else {
                mchCode = CodeUtil.subMerchantCode(clientType, sequence, payMchInfo.getIsvCode());
            }
            payMchInfo.setMchNo(mchCode);
        } finally {
            lock.unlock();
        }

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




