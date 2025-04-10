package com.baosight.payment.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baosight.payment.order.pojo.dto.PayOrderPageDTO;
import com.baosight.payment.order.pojo.entity.PayOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baosight.payment.order.pojo.vo.PayOrderPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author longjiangran
* @description 针对表【pay_order(支付订单表)】的数据库操作Mapper
* @createDate 2025-03-19 14:19:14
* @Entity com.baosight.payment.order.pojo.entity.PayOrder
*/
@Mapper
public interface PayOrderMapper extends BaseMapper<PayOrder> {

    /**
     * 支付订单列表
     * @param page page
     * @param payOrderPage 支付列表请求
     */
    IPage<PayOrderPageVO> payOrderPage(@Param("page") Page<PayOrderPageVO> page, @Param("payOrderPage") PayOrderPageDTO payOrderPage);
}




