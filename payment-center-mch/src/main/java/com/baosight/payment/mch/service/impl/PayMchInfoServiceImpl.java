package com.baosight.payment.mch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.page.PageResponse;
import com.baosight.database.utils.PageUtil;
import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.enums.MchType;
import com.baosight.payment.isv.api.IsvInfoApi;
import com.baosight.payment.isv.vo.IsvInfoVO;
import com.baosight.payment.mch.convert.PayMchInfoConvert;
import com.baosight.payment.mch.error.MchError;
import com.baosight.payment.mch.mapper.PayMchInfoMapper;
import com.baosight.payment.mch.pojo.dto.MchInfoDTO;
import com.baosight.payment.mch.pojo.dto.MchPageDTO;
import com.baosight.payment.mch.pojo.entity.PayMchInfo;
import com.baosight.payment.mch.pojo.vo.PayMchInfoVO;
import com.baosight.payment.mch.pojo.vo.PayMchListVO;
import com.baosight.payment.mch.service.PayMchInfoService;
import com.baosight.payment.utils.IdGenUtil;
import com.baosight.saas.context.SystemUserContext;
import com.baosight.utils.utils.Assert;
import com.baosight.web.exception.ApiException;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author longjiangran
 * @description 针对表【pay_mch_info(支付服务商信息表)】的数据库操作Service实现
 * @createDate 2025-03-17 13:13:10
 */
@Service
@RequiredArgsConstructor
public class PayMchInfoServiceImpl extends ServiceImpl<PayMchInfoMapper, PayMchInfo> implements PayMchInfoService {
    private final PayMchInfoMapper payMchInfoMapper;

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
        if (!CollectionUtils.isEmpty(list)) {
            List<Long> isvId = list.stream().filter(e -> e.getType().equals(MchType.SUB_MERCHANT.getCode())).map(PayMchListVO::getIsvId).toList();
            List<IsvInfoVO> isvInfoVOList = isvInfoApi.isvInfoList(isvId);
            Map<Long, IsvInfoVO> isvInfoMap = isvInfoVOList.stream().collect(Collectors.toMap(IsvInfoVO::getId, e -> e));
            list.stream().filter(e -> e.getType().equals(MchType.SUB_MERCHANT.getCode())).filter(e -> isvInfoMap.containsKey(e.getIsvId()))
                    .forEach(e -> {
                        IsvInfoVO isvInfoVO = isvInfoMap.get(e.getIsvId());
                        e.setIsvName(isvInfoVO.getName());
                    });
        }

        return build;
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
        PayMchInfo payMchInfo = payMchInfoMapper.selectOne(new LambdaQueryWrapper<PayMchInfo>()
                .eq(PayMchInfo::getMchName, mchInfoDTO.getMchName())
                .eq(PayMchInfo::getContactTel, mchInfoDTO.getContactTel()));
        Assert.notNull(payMchInfo, ApiException.supplier(MchError.MCH_INFO_EXIST));
        payMchInfo = PayMchInfoConvert.INSTANCE.toPayMchInfo(mchInfoDTO);
        payMchInfo.setCreateBy(SystemUserContext.getUserId());
        payMchInfo.setCreateByName(SystemUserContext.getUsername());
        if (mchInfoDTO.getType().equals(MchType.MERCHANT.getCode())) {
            payMchInfo.setIsvId(null);
        }

        String prefix = mchInfoDTO.getType().equals(MchType.MERCHANT.getCode()) ? "N" : "S";
        String mchNo = IdGenUtil.generateId(SnowflakeIdUtil.nextId());
        payMchInfo.setMchNo(prefix + mchNo);
        return payMchInfoMapper.insert(payMchInfo) > 0;
    }


    /**
     * 处理特约商户
     */
    private final Consumer<MchInfoDTO> handlerSpecialMch = (e) -> {
        if (e.getType().equals(MchType.SUB_MERCHANT.getCode())) {
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
        PayMchInfoVO result = PayMchInfoConvert.INSTANCE.toPayMchInfoVO(payMchInfo);
        if (result.getType().equals(MchType.SUB_MERCHANT.getCode())) {
            IsvInfoVO isvInfoVO = isvInfoApi.isvInfoById(result.getIsvId());
            result.setIsvName(isvInfoVO.getName());
        }
        return result;
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

        PayMchInfoConvert.INSTANCE.copyPayMchInfo(mchInfoDTO, payMchInfo);
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
}




