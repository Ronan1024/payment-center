package com.baosight.payment.channel.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.channel.convert.ChannelInfoConvert;
import com.baosight.payment.channel.dao.entity.ChannelInfo;
import com.baosight.payment.channel.dao.manager.ChannelInfoManager;
import com.baosight.payment.channel.dao.mapper.ChannelInfoMapper;
import com.baosight.payment.channel.handler.ChannelContext;
import com.baosight.payment.channel.handler.channel.IChannel;
import com.baosight.payment.channel.pojo.dto.ChannelInfoDTO;
import com.baosight.payment.channel.pojo.dto.ChannelInfoPageDTO;
import com.baosight.payment.channel.pojo.vo.ChannelInfoVO;
import com.baosight.payment.channel.service.ChannelInfoService;
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
public class ChannelInfoServiceImpl extends ServiceImpl<ChannelInfoMapper, ChannelInfo> implements ChannelInfoService {

    private final ChannelInfoManager ChannelInfoManager;

    @Override
    public PageResponse<ChannelInfoVO> page(ChannelInfoPageDTO pageDTO) {
        LambdaQueryWrapper<ChannelInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(pageDTO.getChannelName()), ChannelInfo::getChannelName, pageDTO.getChannelName())
                .eq(StringUtils.hasText(pageDTO.getChannelCode()), ChannelInfo::getChannelCode, pageDTO.getChannelCode())
                .eq(pageDTO.getType() != null, ChannelInfo::getType, pageDTO.getType())
                .orderByDesc(ChannelInfo::getCreateTime);
        IPage<ChannelInfo> page = new Page<>(pageDTO.getCurrent(), pageDTO.getSize());
        IPage<ChannelInfo> result = this.page(page, wrapper);
        List<ChannelInfoVO> voList = result.getRecords().stream()
                .map(entity -> BeanUtil.copyProperties(entity, ChannelInfoVO.class))
                .collect(Collectors.toList());
        PageResponse<ChannelInfoVO> response = new PageResponse<>(result.getCurrent(), result.getSize(), result.getPages(), result.getTotal());
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
    public Boolean saveChannelInfo(ChannelInfoDTO dto) {
        ChannelInfo channelInfo = ChannelInfoManager.getOne(new LambdaQueryWrapper<ChannelInfo>()
                .eq(ChannelInfo::getChannelCode, dto.getChannelCode()));
        Assert.notNull(channelInfo, ApiException.supplier(CHANNEL_CODE_BOUND_ALREADY));
        PayingAgency byCode = PayingAgency.getByCode(dto.getChannelAgency());
        Assert.isNull(byCode, "渠道机构异常");
        IChannel channel = ChannelContext.getChannel(dto.getChannelCode());
        Assert.isNull(channel, ApiException.supplier(CHANNEL_CODE_ERROR));
        channelInfo = ChannelInfoConvert.INSTANCE.toChannelInfo(dto);
        channelInfo.setChannelName(channel.channelName());

        return ChannelInfoManager.saveChannelInfo(channelInfo);
    }

    @Override
    public Boolean updateChannelInfo(ChannelInfoDTO dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("渠道ID不能为空");
        }
        ChannelInfo entity = BeanUtil.copyProperties(dto, ChannelInfo.class);
        entity.setUpdateTime(new Date());
        return this.updateById(entity);
    }

    @Override
    public Boolean deleteChannelInfo(Long id) {
        return this.removeById(id);
    }

    @Override
    public ChannelInfoVO getInfoById(Long id) {
        ChannelInfo entity = this.getById(id);
        if (entity == null) {
            return null;
        }
        return BeanUtil.copyProperties(entity, ChannelInfoVO.class);
    }
}




