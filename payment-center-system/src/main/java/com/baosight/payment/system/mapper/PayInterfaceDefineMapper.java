package com.baosight.payment.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.system.pojo.dto.PayInterfaceListDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author longjiangran
 * @description 针对表【pay_interface_define(支付接口定义表)】的数据库操作Mapper
 * @createDate 2025-01-17 16:12:49
 * @Entity com.baosight.payment.pojo.entity.PayInterfaceDefine
 */
@Mapper
public interface PayInterfaceDefineMapper extends BaseMapper<PayInterfaceDefine> {

    IPage<PayInterfaceDefineListVO> page(@Param("page") Page<PayInterfaceDefineListVO> page, @Param("pageDTO") PayInterfaceListDTO pageDTO);

    PayInterfaceDefineVO getInterfaceById(@Param("id")Long id);
}




