package com.baosight.payment.channel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.channel.dao.entity.ChannelInfo;
import com.baosight.payment.channel.pojo.dto.ChannelInfoDTO;
import com.baosight.payment.channel.pojo.dto.ChannelInfoPageDTO;
import com.baosight.payment.channel.pojo.vo.ChannelInfoVO;

/**
 * @author longjiangran
 * @description 针对表【paying_channel_info(支付渠道信息)】的数据库操作Service
 * @createDate 2026-03-04 14:00:52
 */
public interface ChannelInfoService extends IService<ChannelInfo> {

    /**
     * 分页查询支付渠道信息
     *
     * @param pageDTO 分页查询参数
     * @return 分页结果
     */
    PageResponse<ChannelInfoVO> page(ChannelInfoPageDTO pageDTO);

    /**
     * 新增支付渠道信息
     *
     * @param dto 渠道信息
     * @return 是否成功
     */
    Boolean saveChannelInfo(ChannelInfoDTO dto);

    /**
     * 更新支付渠道信息
     *
     * @param dto 渠道信息
     * @return 是否成功
     */
    Boolean updateChannelInfo(ChannelInfoDTO dto);

    /**
     * 删除支付渠道信息
     *
     * @param id 渠道ID
     * @return 是否成功
     */
    Boolean deleteChannelInfo(Long id);

    /**
     * 根据ID获取支付渠道信息详情
     *
     * @param id 渠道ID
     * @return 渠道信息
     */
    ChannelInfoVO getInfoById(Long id);
}
