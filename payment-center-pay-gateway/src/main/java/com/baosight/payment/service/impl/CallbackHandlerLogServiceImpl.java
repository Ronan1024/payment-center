package com.baosight.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baosight.payment.mapper.CallbackHandlerLogMapper;
import com.baosight.payment.pojo.entity.CallbackHandlerLog;
import com.baosight.payment.service.CallbackHandlerLogService;
import com.baosight.utils.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author longjiangran
 * @description 针对表【callback_handler_log(回调处理记录表)】的数据库操作Service实现
 * @createDate 2025-03-19 14:36:31
 */
@Service
@RequiredArgsConstructor
public class CallbackHandlerLogServiceImpl extends ServiceImpl<CallbackHandlerLogMapper, CallbackHandlerLog> implements CallbackHandlerLogService {

    private final CallbackHandlerLogMapper callbackHandlerLogMapper;

    /**
     * 根据支付机构、支付类型、商户号和交易ID获取回调处理记录
     *
     * @param payingAgency 支付机构
     * @param payType      支付类型
     * @param trxId        交易id
     */
    @Override
    public CallbackHandlerLog getInfo(Integer payingAgency, Integer payType, String trxId, String interfaceCode) {
        return callbackHandlerLogMapper.selectOne(new LambdaQueryWrapper<CallbackHandlerLog>()
                .eq(CallbackHandlerLog::getHasHandler, Boolean.TRUE)
                .eq(CallbackHandlerLog::getTrxId, trxId)
                .eq(CallbackHandlerLog::getPayType, payType)
                .eq(CallbackHandlerLog::getPayingAgency, payingAgency)
                .eq(CallbackHandlerLog::getInterfaceCode, interfaceCode)
        );

    }

    /**
     * 更新回调处理状态
     *
     * @param state 状态
     * @param error 异常信息
     * @param id    回调id
     */
    @Override
    public Boolean updateHandlerState(Boolean state, String error, Long id) {
        CallbackHandlerLog callbackHandlerLog = callbackHandlerLogMapper.selectById(id);
        if (ObjectUtils.isEmpty(callbackHandlerLog)) {
            return Boolean.FALSE;
        }

        return callbackHandlerLogMapper.update(new LambdaUpdateWrapper<CallbackHandlerLog>()
                .eq(CallbackHandlerLog::getId, id)
                .set(CallbackHandlerLog::getHasHandler, state)
                .set(StringUtils.hasText(error), CallbackHandlerLog::getHandlerError, error)
        ) > 0;
    }
}




