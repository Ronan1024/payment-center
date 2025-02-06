package com.baosight.payment.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.pojo.dto.PayWayPageDTO;
import com.baosight.payment.pojo.entity.PayWay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.pojo.vo.PayWayPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author longjiangran
* @description 针对表【pay_way(支付方式表)】的数据库操作Mapper
* @createDate 2025-01-16 14:12:42
* @Entity com.baosight.payment.pojo.entity.PayWay
*/
@Mapper
public interface PayWayMapper extends BaseMapper<PayWay> {

    /**
     * 获取支付方式列表
     * @param page page
     * @param payWayDTO  支付方式查询请求
     */
    IPage<PayWayPageVO> page(@Param("page") Page<PayWayPageVO> page, @Param("payWayDTO") PayWayPageDTO payWayDTO);
}




