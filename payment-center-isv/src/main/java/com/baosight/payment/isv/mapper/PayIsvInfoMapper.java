package com.baosight.payment.isv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.isv.pojo.dto.IsvPageDTO;
import com.baosight.payment.isv.pojo.entity.PayIsvInfo;
import com.baosight.payment.isv.pojo.vo.PayIsvPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author longjiangran
 * @description 针对表【pay_isv_info(服务商信息表)】的数据库操作Mapper
 * @createDate 2025-03-15 19:50:10
 * @Entity com.baosight.payment.pojo.entity.PayIsvInfo
 */
@Mapper
public interface PayIsvInfoMapper extends BaseMapper<PayIsvInfo> {


    IPage<PayIsvPageVO> page(@Param("page") Page<PayIsvPageVO> page, @Param("isvPageDTO") IsvPageDTO isvPageDTO);

}




