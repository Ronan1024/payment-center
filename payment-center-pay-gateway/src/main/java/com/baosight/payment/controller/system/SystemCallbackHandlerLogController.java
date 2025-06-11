package com.baosight.payment.controller.system;

import com.baosight.payment.pojo.dto.CallbackHandlerLogDTO;
import com.baosight.payment.pojo.vo.CallbackHandlerLogVO;
import com.baosight.payment.service.CallbackHandlerLogService;
import com.baosight.saas.constant.BaseUrlConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 回调日志处理
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/6/11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseUrlConstant.SYSTEM + "/pm/pay/callback_handler/record")
public class SystemCallbackHandlerLogController {

    private final CallbackHandlerLogService callbackHandlerLogService;


    /**
     * 获取支付接口列表
     */
    @PostMapping("/page")
    public List<CallbackHandlerLogVO> page(@RequestBody @Validated CallbackHandlerLogDTO pageDTO) {
//        return callbackHandlerLogService.payInterfacePage(pageDTO);
        return null;
    }


}
