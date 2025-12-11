package com.baosight.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.pojo.dto.CallbackHandlerLogDTO;
import com.baosight.payment.pojo.entity.CallbackHandlerLog;
import com.baosight.payment.pojo.vo.CallbackHandlerLogDetailVO;
import com.baosight.payment.pojo.vo.CallbackHandlerLogVO;

/**
 * @author longjiangran
 * @description 针对表【callback_handler_log(回调处理记录表)】的数据库操作Service
 * @createDate 2025-03-19 14:36:31
 */
public interface CallbackHandlerLogService extends IService<CallbackHandlerLog> {

    /**
     * 根据支付机构、支付类型、商户号和交易ID获取回调处理记录
     *
     * @param payingAgency 支付机构
     * @param payType      支付类型
     * @param mchNo        商户号
     * @param trxId        交易id
     */
    CallbackHandlerLog getInfo(Integer payingAgency, Integer payType, String mchNo, String trxId);

    /**
     * 更新回调处理状态
     *
     * @param state 状态
     * @param error 异常信息
     * @param id    回调id
     */
    Boolean updateHandlerState(Boolean state, String error, Long id);

    /**
     * 分页查询回调处理记录
     *
     * @param pageDTO 分页查询参数
     */
    PageResponse<CallbackHandlerLogVO> callbackHandlerPage(CallbackHandlerLogDTO pageDTO);


    /**
     * 根据ID获取回调处理记录详情
     *
     * @param id 回调ID
     */
    CallbackHandlerLogDetailVO detail(Long id);
}
