package com.baosight.payment.accounting.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.accounting.pojo.dto.ChannelBillDTO;
import com.baosight.payment.accounting.pojo.entity.ChannelBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.accounting.pojo.vo.ChannelBillListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author longjiangran
* @description 针对表【channel_bill(渠道账单)】的数据库操作Mapper
* @createDate 2025-04-04 22:01:16
* @Entity com.baosight.payment.recon.pojo.entity.ChannelBill
*/
@Mapper
public interface ChannelBillMapper extends BaseMapper<ChannelBill> {

    /**
     * 渠道账单列表
     *
     * @param channelBill 渠道账单列表请求体
     */
    IPage<ChannelBillListVO> channelBillPage(@Param("page") Page<ChannelBillListVO> page, @Param("channelBill") ChannelBillDTO channelBill);
}




