package com.baosight.payment.channel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.channel.convert.PayingChannelInfoConvert;
import com.baosight.payment.channel.dao.entity.PayingChannelInfo;
import com.baosight.payment.channel.dao.manager.PayingChannelInfoManager;
import com.baosight.payment.channel.dao.mapper.PayingChannelInfoMapper;
import com.baosight.payment.channel.handler.ChannelContext;
import com.baosight.payment.channel.handler.IChannel;
import com.baosight.payment.channel.pojo.dto.PayingChannelInfoDTO;
import com.baosight.payment.channel.pojo.dto.PayingChannelInfoPageDTO;
import com.baosight.payment.channel.pojo.vo.PayingChannelInfoVO;
import com.baosight.payment.channel.service.PayingChannelInfoService;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.baosight.payment.channel.error.ChannelError.CHANNEL_CODE_BOUND_ALREADY;
import static com.baosight.payment.channel.error.ChannelError.CHANNEL_CODE_ERROR;

/**
 * @author longjiangran
 * @description 针对表【paying_channel_info(支付渠道信息)】的数据库操作Service实现
 * @createDate 2026-03-04 14:00:52
 */
@Service
@RequiredArgsConstructor
public class PayingChannelInfoServiceImpl extends ServiceImpl<PayingChannelInfoMapper, PayingChannelInfo> implements PayingChannelInfoService {

    private final PayingChannelInfoManager payingChannelInfoManager;

    @Override
    public PageResponse<PayingChannelInfoVO> page(PayingChannelInfoPageDTO pageDTO) {
        LambdaQueryWrapper<PayingChannelInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(pageDTO.getChannelName()), PayingChannelInfo::getChannelName, pageDTO.getChannelName())
                .eq(StringUtils.hasText(pageDTO.getChannelCode()), PayingChannelInfo::getChannelCode, pageDTO.getChannelCode())
                .eq(pageDTO.getType() != null, PayingChannelInfo::getType, pageDTO.getType())
                .orderByDesc(PayingChannelInfo::getCreateTime);
        IPage<PayingChannelInfo> page = new Page<>(pageDTO.getCurrent(), pageDTO.getSize());
        IPage<PayingChannelInfo> result = this.page(page, wrapper);
        List<PayingChannelInfoVO> voList = result.getRecords().stream()
                .map(entity -> BeanUtil.copyProperties(entity, PayingChannelInfoVO.class))
                .collect(Collectors.toList());
        PageResponse<PayingChannelInfoVO> response = new PageResponse<>(result.getCurrent(), result.getSize(), result.getPages(), result.getTotal());
        response.setList(voList);
        return response;
    }

    /**
     * 新增支付渠道信息
     *
     * @param dto 渠道信息
     * @return 是否成功
     */
    @Override
    public Boolean savePayingChannelInfo(PayingChannelInfoDTO dto) {
        PayingChannelInfo channelInfo = payingChannelInfoManager.getOne(new LambdaQueryWrapper<PayingChannelInfo>()
                .eq(PayingChannelInfo::getChannelCode, dto.getChannelCode()));
        Assert.notNull(channelInfo, ApiException.supplier(CHANNEL_CODE_BOUND_ALREADY));
        PayingAgency byCode = PayingAgency.getByCode(dto.getChannelAgency());
        Assert.isNull(byCode, "渠道机构异常");
        IChannel channel = ChannelContext.getChannel(dto.getChannelCode());
        Assert.isNull(channel, ApiException.supplier(CHANNEL_CODE_ERROR));
        PayingChannelInfo payingChannelInfo = PayingChannelInfoConvert.INSTANCE.toPayingChannelInfo(dto);
        payingChannelInfo.setChannelName(channel.channelName());

        return payingChannelInfoManager.savePayingChannelInfo(payingChannelInfo);
    }

    @Override
    public Boolean updatePayingChannelInfo(PayingChannelInfoDTO dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("渠道ID不能为空");
        }
        PayingChannelInfo entity = BeanUtil.copyProperties(dto, PayingChannelInfo.class);
        entity.setUpdateTime(new Date());
        return this.updateById(entity);
    }

    @Override
    public Boolean deletePayingChannelInfo(Long id) {
        return this.removeById(id);
    }

    @Override
    public PayingChannelInfoVO getInfoById(Long id) {
        PayingChannelInfo entity = this.getById(id);
        if (entity == null) {
            return null;
        }
        return BeanUtil.copyProperties(entity, PayingChannelInfoVO.class);
    }
}




