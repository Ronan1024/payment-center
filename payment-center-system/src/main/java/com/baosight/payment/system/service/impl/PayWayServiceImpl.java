package com.baosight.payment.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.page.PageResponse;
import com.baosight.database.utils.PageUtil;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.system.convert.PayWayConvert;
import com.baosight.payment.system.error.PayWayError;
import com.baosight.payment.system.mapper.PayWayMapper;
import com.baosight.payment.system.pojo.dto.PayWayPageDTO;
import com.baosight.payment.system.pojo.dto.SavePayWayDTO;
import com.baosight.payment.system.pojo.entity.PayWay;
import com.baosight.payment.system.pojo.vo.PayWayPageVO;
import com.baosight.payment.system.pojo.vo.PayWayVO;
import com.baosight.payment.system.service.PayWayService;
import com.baosight.saas.context.AbstractUserContext;
import com.baosight.utils.enums.IBaseEnum;
import com.baosight.utils.utils.Assert;
import com.baosight.web.exception.ApiException;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_way(支付方式表)】的数据库操作Service实现
 * @createDate 2025-01-16 14:12:42
 */
@Service
@RequiredArgsConstructor
public class PayWayServiceImpl extends ServiceImpl<PayWayMapper, PayWay> implements PayWayService {
    private final PayWayMapper payWayMapper;
    @Resource
    private MchInfoApi mchInfoApi;

    /**
     * 获取支付方式列表
     *
     * @param payWayDTO 支付方式列表请求
     */
    @Override
    public PageResponse<PayWayPageVO> pagePayWay(PayWayPageDTO payWayDTO) {
        PageUtil<PayWayPageVO> pageUtil = new PageUtil<>(payWayDTO);
        PageResponse<PayWayPageVO> build = pageUtil.builder(payWayMapper.page(pageUtil.Page(), payWayDTO)).build();
        build.getList().forEach(e -> e.setPayingAgencyName(IBaseEnum.getMsg(PayingAgency.class, e.getPayingAgency())));
        return build;
    }

    /**
     * 保存支付方式
     *
     * @param payWayDTO 支付方式保存信息
     */
    @Override
    public Boolean savePayWay(SavePayWayDTO payWayDTO) {
        Long count = payWayMapper.selectCount(new LambdaQueryWrapper<PayWay>()
                .eq(PayWay::getPayCode, payWayDTO.getPayCode()));
        Assert.isTrue(count > 0, () -> new ApiException(PayWayError.PAY_WAY_CODE_EXIST));
        PayingAgency payingAgency = IBaseEnum.getByCode(PayingAgency.class, payWayDTO.getPayingAgency());
        Assert.isNull(payingAgency, () -> new ApiException(PayWayError.PAY_WAY_AGENCY_ERROR));
        PayWay payWay = new PayWay();
        payWay.setPayCode(payWayDTO.getPayCode());
        payWay.setPayName(payWayDTO.getPayName());
        payWay.setCreateByName(AbstractUserContext.getUsername());
        payWay.setCreateBy(AbstractUserContext.getUserId());
        payWay.setPayingAgency(payingAgency.getCode());
        payWay.setPayingClient(payWayDTO.getPayingClient());
        return payWayMapper.insert(payWay) > 0;
    }

    /**
     * 更新支付方式
     *
     * @param id        支付方式id
     * @param payWayDTO 支付方式请求数据
     */
    @Override
    public Boolean updatePayWay(Long id, SavePayWayDTO payWayDTO) {
        PayWay payWay = payWayMapper.selectById(id);
        Assert.isNull(payWay, () -> new ApiException(PayWayError.PAY_WAY_NOT_FOUND));
        PayingAgency payingAgency = IBaseEnum.getByCode(PayingAgency.class, payWayDTO.getPayingAgency());
        Assert.isNull(payingAgency, () -> new ApiException(PayWayError.PAY_WAY_AGENCY_ERROR));
        Long count = payWayMapper.selectCount(new LambdaQueryWrapper<PayWay>()
                .eq(PayWay::getPayCode, payWayDTO.getPayCode())
                .eq(PayWay::getPayName, payWayDTO.getPayName())
                .ne(PayWay::getId, payWay.getId()));
        Assert.isTrue(count > 0, () -> new ApiException(PayWayError.PAY_WAY_CODE_EXIST));
        payWay.setPayName(payWayDTO.getPayName());
        payWay.setPayCode(payWayDTO.getPayCode());
        payWay.setUpdateBy(AbstractUserContext.getUserId());
        payWay.setUpdateByName(AbstractUserContext.getUsername());
        payWay.setPayingAgency(payingAgency.getCode());
        payWay.setPayingClient(payWayDTO.getPayingClient());
        return payWayMapper.updateById(payWay) > 0;
    }

    /**
     * 删除支付方式
     *
     * @param id 支付方式id
     */
    @Override
    public Boolean deletePayWay(Long id) {
        // TODO 校验该支付方式是否有商户已配置通道或者已有订单
//        if (mchPayPassageService.count(MchPayPassage.gw().eq(MchPayPassage::getWayCode, wayCode)) > 0
//                || payOrderService.count(PayOrder.gw().eq(PayOrder::getWayCode, wayCode)) > 0) {
//            throw new BizException("该支付方式已有商户配置通道或已发生交易，无法删除！");
//        }
        return payWayMapper.deleteById(id) > 0;
    }

    /**
     * 支付方式详情
     *
     * @param id 支付方式id
     * @return 支付方式信息
     */
    @Override
    public PayWayVO detailPayWay(Long id) {
        PayWay payWay = payWayMapper.selectById(id);
        Assert.isNull(payWay, () -> new ApiException(PayWayError.PAY_WAY_NOT_FOUND));
        return PayWayConvert.INSTANCE.toPayWayVO(payWay);
    }

    /**
     * 禁用支付方式
     *
     * @param id 支付方式ID
     */
    @Override
    public Boolean disablePayWay(Long id) {
        PayWay payWay = payWayMapper.selectById(id);
        Assert.isNull(payWay, () -> new ApiException(PayWayError.PAY_WAY_NOT_FOUND));
        payWay.setDisable(!payWay.getDisable());
        payWay.setUpdateBy(AbstractUserContext.getUserId());
        return payWayMapper.updateById(payWay) > 0;
    }

    /**
     * 获取支付方式列表
     */
    @Override
    public List<PayWayPageVO> pagePayList() {
        List<PayWay> payWays = payWayMapper.selectList(new LambdaQueryWrapper<PayWay>()
                .eq(PayWay::getDisable, Boolean.FALSE));
        return payWays.stream().map(PayWayConvert.INSTANCE::toPayWayPageVO).toList();
    }

    /**
     * 验证支付方式是否存在
     *
     * @param payWayList 支付方式列表
     */
    @Override
    public void verify(List<Long> payWayList) {
        List<PayWay> payWay = payWayMapper.selectByIds(payWayList);
        Assert.isFalse(payWay.size() == payWayList.size(), () -> new ApiException(PayWayError.PAY_WAY_NOT_FOUND));
    }

    /**
     * 获取支付方式列表信息
     *
     * @param payWayIdList 支付方式id列表
     */
    @Override
    public List<PayWay> selectByIdList(List<Long> payWayIdList) {
        return payWayMapper.selectByIds(payWayIdList);
    }

    /**
     * 获取支付方式信息
     *
     * @param payWayIdList 支付方式id 列表
     */
    @Override
    public List<PayWay> getPayWayList(List<Long> payWayIdList) {
        return payWayMapper.selectList(new LambdaQueryWrapper<PayWay>()
                .in(PayWay::getId, payWayIdList)
                .eq(PayWay::getDisable, Boolean.FALSE)
        );
    }

    /**
     * 支付方式列表
     *
     * @param payWayIdList 支付方式id
     * @param payClient    支付客户端
     */
    @Override
    public List<PayWay> getPayWayList(List<Long> payWayIdList, Integer payClient) {
        return payWayMapper.selectList(new LambdaQueryWrapper<PayWay>()
                .in(PayWay::getId, payWayIdList)
                .eq(PayWay::getPayingClient, payClient)
        );
    }

}




