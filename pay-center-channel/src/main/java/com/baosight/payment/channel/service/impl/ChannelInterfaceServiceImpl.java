package com.baosight.payment.channel.service.impl;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.channel.convert.ChannelInterfaceConvert;
import com.baosight.payment.channel.dao.entity.ChannelInterface;
import com.baosight.payment.channel.dao.manager.ChannelInterfaceManager;
import com.baosight.payment.channel.enums.InterfaceStatusEnum;
import com.baosight.payment.channel.error.BaseErrorCode;
import com.baosight.payment.channel.error.ChannelInterfaceError;
import com.baosight.payment.channel.pojo.dto.req.ChannelInterfacePageReqDTO;
import com.baosight.payment.channel.pojo.dto.req.ChannelInterfaceStatusUpdateReqDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelInterfaceInfoRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelInterfacePageRespDTO;
import com.baosight.payment.channel.service.ChannelInterfaceService;
import com.baosight.web.core.exception.ApiException;
import com.ronan.common.enums.IBaseEnum;
import com.ronan.common.utils.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 渠道接口管理服务实现。
 *
 * <p>
 * 当前实现聚焦运营端读写场景：分页查询走 Mapper 自定义 SQL，详情通过主键读取实体后转换为
 * 详情响应对象，状态修改只更新 ID 和状态字段，避免覆盖接口其它配置。
 * </p>
 *
 * @author longjiangran
 * @description 针对表【channel_interface(渠道接口能力表)】的数据库操作Service实现
 * @createDate 2026-06-12 09:26:54
 */
@Service
@RequiredArgsConstructor
public class ChannelInterfaceServiceImpl implements ChannelInterfaceService {

    private final ChannelInterfaceManager channelInterfaceManager;

    @Override
    public PageResponse<ChannelInterfacePageRespDTO> page(ChannelInterfacePageReqDTO req) {
        return channelInterfaceManager.page(req);
    }

    @Override
    public ChannelInterfaceInfoRespDTO info(Long id) {
        ChannelInterface channelInterface = channelInterfaceManager.getById(id);
        Assert.isNull(channelInterface, ApiException.supplier(BaseErrorCode.DATA_EXCEPTION));
        return ChannelInterfaceConvert.INSTANCE.toChannelInterfaceInfoRespDTO(channelInterface);
    }

    @Override
    public Boolean updateStatus(ChannelInterfaceStatusUpdateReqDTO req) {
        if (IBaseEnum.getByCode(InterfaceStatusEnum.class, req.getStatus()) == null) {
            throw new ApiException(BaseErrorCode.DATA_EXCEPTION);
        }
        ChannelInterface anInterface = channelInterfaceManager.getById(req.getId());

        Assert.isNull(anInterface, ApiException.supplier(BaseErrorCode.DATA_EXCEPTION));


        ChannelInterface channelInterface = new ChannelInterface();
        channelInterface.setId(req.getId());
        channelInterface.setStatus(req.getStatus());
        if (!req.getStatus().equals(InterfaceStatusEnum.ENABLE.code()) && !StringUtils.hasText(req.getRemark())) {
            throw new ApiException(ChannelInterfaceError.REMARK_NULL_ERROR);
        }
        return channelInterfaceManager.updateById(channelInterface);
    }

}



