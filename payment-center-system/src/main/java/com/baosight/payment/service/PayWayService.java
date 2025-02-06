package com.baosight.payment.service;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.pojo.dto.PayWayPageDTO;
import com.baosight.payment.pojo.dto.SavePayWayDTO;
import com.baosight.payment.pojo.entity.PayWay;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.pojo.vo.PayWayPageVO;
import com.baosight.payment.pojo.vo.PayWayVO;

import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_way(支付方式表)】的数据库操作Service
 * @createDate 2025-01-16 14:12:42
 */
public interface PayWayService extends IService<PayWay> {

    /**
     * 获取支付方式列表
     *
     * @param payWayDTO 支付方式列表请求
     */
    PageResponse<PayWayPageVO> pagePayWay(PayWayPageDTO payWayDTO);

    /**
     * 保存支付方式
     *
     * @param payWayDTO 支付方式保存信息
     */
    Boolean savePayWay(SavePayWayDTO payWayDTO);

    /**
     * 更新支付方式
     *
     * @param id        支付方式id
     * @param payWayDTO 支付方式请求数据
     */
    Boolean updatePayWay(Long id, SavePayWayDTO payWayDTO);

    /**
     * 删除支付方式
     *
     * @param id 支付方式id
     */
    Boolean deletePayWay(Long id);

    /**
     * 支付方式详情
     *
     * @param id 支付方式id
     * @return 支付方式信息
     */
    PayWayVO detailPayWay(Long id);

    /**
     * 禁用支付方式
     *
     * @param id 支付方式ID
     */
    Boolean disablePayWay(Long id);

    /**
     * 获取支付方式列表
     */
    List<PayWayPageVO> pagePayList();

    /**
     * 验证支付方式是否存在
     */
    void verify(List<Long> payWayList);
}
