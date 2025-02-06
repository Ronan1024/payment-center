package com.baosight.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.page.PageResponse;
import com.baosight.database.utils.PageUtil;
import com.baosight.payment.convert.PayWayConvert;
import com.baosight.payment.error.PayWayError;
import com.baosight.payment.pojo.dto.PayWayPageDTO;
import com.baosight.payment.pojo.dto.SavePayWayDTO;
import com.baosight.payment.pojo.entity.PayWay;
import com.baosight.payment.pojo.vo.PayWayPageVO;
import com.baosight.payment.pojo.vo.PayWayVO;
import com.baosight.payment.service.PayWayService;
import com.baosight.payment.mapper.PayWayMapper;
import com.baosight.saas.context.SystemUserContext;
import com.baosight.utils.utils.Assert;
import com.baosight.web.exception.ApiException;
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

    /**
     * 获取支付方式列表
     *
     * @param payWayDTO 支付方式列表请求
     */
    @Override
    public PageResponse<PayWayPageVO> pagePayWay(PayWayPageDTO payWayDTO) {
        PageUtil<PayWayPageVO> pageUtil = new PageUtil<>(payWayDTO);
        return pageUtil.builder(payWayMapper.page(pageUtil.Page(), payWayDTO)).build();
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
        Assert.isTrue(count > 0, () -> new ApiException(PayWayError.PAY_WAY_NOT_FOUND));
        PayWay payWay = new PayWay();
        payWay.setPayCode(payWayDTO.getPayCode());
        payWay.setPayName(payWayDTO.getPayName());
        payWay.setCreateBy(SystemUserContext.getUserId());
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
        Long count = payWayMapper.selectCount(new LambdaQueryWrapper<PayWay>()
                .eq(PayWay::getPayCode, payWayDTO.getPayCode())
                .eq(PayWay::getPayName, payWayDTO.getPayName())
                .ne(PayWay::getId, payWay.getId()));
        Assert.isTrue(count > 0, () -> new ApiException(PayWayError.PAY_WAY_CODE_EXIST));
        payWay.setPayName(payWayDTO.getPayName());
        payWay.setPayCode(payWayDTO.getPayCode());
        payWay.setUpdateBy(SystemUserContext.getUserId());
        return payWayMapper.updateById(payWay) > 0;
    }

    /**
     * 删除支付方式
     *
     * @param id 支付方式id
     */
    @Override
    public Boolean deletePayWay(Long id) {
        // 校验该支付方式是否有商户已配置通道或者已有订单
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
        payWay.setUpdateBy(SystemUserContext.getUserId());
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


}




