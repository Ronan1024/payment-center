package com.baosight.payment.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.pojo.dto.IsvPageDTO;
import com.baosight.payment.pojo.entity.PayIsvInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.pojo.vo.PayIsvPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author longjiangran
 * @description 针对表【pay_isv_info(支付服务商信息表)】的数据库操作Mapper
 * @createDate 2025-02-06 11:09:36
 * @Entity com.baosight.payment.pojo.entity.PayIsvInfo
 */
@Mapper
public interface PayIsvInfoMapper extends BaseMapper<PayIsvInfo> {

    /**
     * 获取服务商列表
     *
     * @param page       page
     * @param isvPageDTO 请求参数
     */
    IPage<PayIsvPageVO> page(@Param("page") Page<PayIsvPageVO> page, @Param("isvPageDTO") IsvPageDTO isvPageDTO);
}




