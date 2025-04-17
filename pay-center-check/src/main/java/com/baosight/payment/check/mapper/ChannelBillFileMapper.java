package com.baosight.payment.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.check.pojo.dto.ChannelBillFilePageDTO;
import com.baosight.payment.check.pojo.entity.ChannelBillFile;
import com.baosight.payment.check.pojo.vo.ChannelBillFileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author longjiangran
* @description 针对表【channel_bill_file(渠道账单文件)】的数据库操作Mapper
* @createDate 2025-04-07 14:36:36
* @Entity com.baosight.payment.check.pojo.entity.ChannelBillFile
*/
@Mapper
public interface ChannelBillFileMapper extends BaseMapper<ChannelBillFile> {


    /**
     * 获取渠道账单文件列表
     * @param page page
     * @param channelBillFilePage 渠道账单文件列表
     */
    IPage<ChannelBillFileVO> channelBillPage(@Param("page") Page<ChannelBillFileVO> page, @Param("channelBillFilePage") ChannelBillFilePageDTO channelBillFilePage);

}




