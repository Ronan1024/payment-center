package com.baosight.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.pojo.dto.PayInterfaceTypeDTO;
import com.baosight.payment.pojo.dto.PayInterfaceTypePageDTO;
import com.baosight.payment.pojo.entity.PayInterfaceType;
import com.baosight.payment.pojo.vo.PayInterfaceTypeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 接口类型配置表 Mapper 接口
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2025-12-16
 */
@Mapper
public interface PayInterfaceTypeMapper extends BaseMapper<PayInterfaceType> {


    /**
     * 获取接口类型分页数据
     * @param page
     * @param pageDTO
     * @return
     */
    IPage<PayInterfaceTypeVO> page(@Param("page") Page<PayInterfaceTypeVO> page, @Param("pageDTO") PayInterfaceTypePageDTO pageDTO);

}
