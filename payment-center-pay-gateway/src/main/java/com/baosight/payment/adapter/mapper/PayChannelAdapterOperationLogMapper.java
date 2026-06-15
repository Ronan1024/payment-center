package com.baosight.payment.adapter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.adapter.pojo.entity.PayChannelAdapterOperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通道适配器运行控制操作日志 Mapper。
 */
@Mapper
public interface PayChannelAdapterOperationLogMapper extends BaseMapper<PayChannelAdapterOperationLog> {
}
