package com.baosight.payment.isv.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.core.page.PageResponse;
import com.baosight.database.core.page.PageUtil;
import com.baosight.payment.enums.MchType;
import com.baosight.payment.enums.State;
import com.baosight.payment.isv.convert.PayIsvInfoConvert;
import com.baosight.payment.isv.error.IsvError;
import com.baosight.payment.isv.mapper.PayIsvInfoMapper;
import com.baosight.payment.isv.pojo.dto.CreateIsvDTO;
import com.baosight.payment.isv.pojo.dto.IsvPageDTO;
import com.baosight.payment.pojo.entity.PayEnterpriseInfo;
import com.baosight.payment.isv.pojo.entity.PayIsvInfo;
import com.baosight.payment.isv.pojo.vo.PayIsvInfoVO;
import com.baosight.payment.isv.pojo.vo.PayIsvPageVO;
import com.baosight.payment.isv.service.PayIsvInfoService;
import com.baosight.saas.auth.context.UserContext;
import com.baosight.saas.tenant.api.TenantInfoApi;
import com.baosight.saas.tenant.api.vo.TenantDetailInfoVO;
import com.baosight.utils.utils.Assert;
import com.baosight.web.core.exception.ApiException;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.baosight.payment.mapper.PayEnterpriseInfoMapper;

import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_isv_info(服务商信息表)】的数据库操作Service实现
 * @createDate 2025-03-15 19:50:10
 */
@Service
@RequiredArgsConstructor
public class PayIsvInfoServiceImpl extends ServiceImpl<PayIsvInfoMapper, PayIsvInfo> implements PayIsvInfoService {


    private final PayIsvInfoMapper payIsvInfoMapper;
    private final PayEnterpriseInfoMapper payEnterpriseInfoMapper;

    @Resource
    private TenantInfoApi tenantInfoApi;
    /**
     * 获取服务商列表
     *
     * @param isvPageDTO 服务商列表请求参数
     * @return 服务商列表信息
     */
    @Override
    public PageResponse<PayIsvPageVO> isvPage(IsvPageDTO isvPageDTO) {
        PageUtil<PayIsvPageVO> pageUtil = new PageUtil<>(isvPageDTO);
        return pageUtil.builder(payIsvInfoMapper.page(pageUtil.Page(), isvPageDTO)).build();
    }

    /**
     * 获取服务商信息详情
     *
     * @param id 服务商id
     * @return 服务商详情
     */
    @Override
    public PayIsvInfoVO info(Long id) {
        PayIsvInfo payIsvInfo = payIsvInfoMapper.selectById(id);
        if (payIsvInfo == null) {
            return null;
        }

        // 补充企业及法人信息
        PayEnterpriseInfo payEnterpriseInfo = payEnterpriseInfoMapper.selectById(payIsvInfo.getEnterpriseInfoId());
        PayIsvInfoVO payIsvInfoVO = PayIsvInfoConvert.INSTANCE.toPayIsvInfoVO(payIsvInfo, payEnterpriseInfo);
        payIsvInfoVO.setId(id);
        return payIsvInfoVO;

    }

    /**
     * 新增服务商信息
     *
     * @param createIsvDTO 服务商请求信息
     */
    @Override
    public Boolean createIsv(CreateIsvDTO createIsvDTO) {
        PayIsvInfo payIsvInfo = payIsvInfoMapper.selectOne(new LambdaQueryWrapper<PayIsvInfo>()
                .eq(PayIsvInfo::getContactTel, createIsvDTO.getContactTel())
                .eq(PayIsvInfo::getName, createIsvDTO.getName()));
        if (!ObjectUtils.isEmpty(payIsvInfo)) {
            Assert.notNull(payIsvInfo, ApiException.supplier(IsvError.ISV_INFO_EXIST));
        }
        payIsvInfo = PayIsvInfoConvert.INSTANCE.toPayIsvInfo(createIsvDTO);
        payIsvInfo.setCreateBy(UserContext.INSTANCE.userId());
        payIsvInfo.setCreateByName(UserContext.INSTANCE.username());

        PayEnterpriseInfo payEnterpriseInfo = PayIsvInfoConvert.INSTANCE.toPayEnterpriseInfo(createIsvDTO);
        payEnterpriseInfoMapper.insert(payEnterpriseInfo);

        payIsvInfo.setEnterpriseInfoId(payEnterpriseInfo.getId());
        return payIsvInfoMapper.insert(payIsvInfo) > 0;
    }

    /**
     * 禁用｜启用 服务商
     *
     * @param isvId 服务商id
     */
    @Override
    public Boolean enable(Long isvId) {
        PayIsvInfo payIsvInfo = payIsvInfoMapper.selectById(isvId);
        Assert.isNull(payIsvInfo, ApiException.supplier(IsvError.ISV_DATA_ERROR));
        Integer state = payIsvInfo.getState().equals(State.NORMAL.code()) ? State.FORBIDDEN.code() : State.NORMAL.code();
        payIsvInfo.setState(state);
        return payIsvInfoMapper.updateById(payIsvInfo) > 0;
    }

    /**
     * 获取商户信息
     *
     * @param id id
     * @return boolean
     */
    @Override
    public PayIsvInfo infoById(Long id) {
        return payIsvInfoMapper.selectById(id);
    }

    /**
     * 根据id 列表获取服务商信息
     *
     * @param isvIdList
     */
    @Override
    public List<PayIsvInfo> infoByIdList(List<Long> isvIdList) {
        return payIsvInfoMapper.selectByIds(isvIdList);
    }

    /**
     * 获取所有服务商列表
     */
    @Override
    public List<PayIsvPageVO> isvList() {
        List<PayIsvInfo> payIsvInfoList = payIsvInfoMapper.selectList(new LambdaQueryWrapper<PayIsvInfo>()
                .eq(PayIsvInfo::getState, State.NORMAL.code())
        );
        return payIsvInfoList.stream().map(PayIsvInfoConvert.INSTANCE::toPayIsvPageVO).toList();

    }

    @Override
    public PayIsvInfoVO tenantIsvInfo(Long id) {
        TenantDetailInfoVO tenantDetailInfo = tenantInfoApi.getTenantDetailInfo(id);
        return PayIsvInfoConvert.INSTANCE.toPayIsvInfoVO(tenantDetailInfo);
    }


}




