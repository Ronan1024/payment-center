package com.baosight.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.pojo.entity.PayInterfaceDefine;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author longjiangran
 * @description 针对表【pay_interface_define(支付接口定义表)】的数据库操作Mapper
 * @createDate 2025-01-17 16:12:49
 * @Entity com.baosight.payment.pojo.entity.PayInterfaceDefine
 */
@Mapper
public interface PayInterfaceDefineMapper extends BaseMapper<PayInterfaceDefine> {

//    /**
//     * 获取支付接口列表
//     */
//    IPage<PayInterfaceDefinePageVO> page(@Param("page") Page<PayInterfaceDefinePageVO> page, @Param("pageDTO") PayInterfacePageDTO pageDTO);
}




