package com.baosight.payment.controller.system;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.pojo.dto.CallbackHandlerLogDTO;
import com.baosight.payment.pojo.vo.CallbackHandlerLogDetailVO;
import com.baosight.payment.pojo.vo.CallbackHandlerLogVO;
import com.baosight.payment.service.CallbackHandlerLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
//@RequestMapping(BaseUrlConstant.SYSTEM + "/pm/pay/callback_handler/record")
@RequestMapping( "/pm/pay/callback_handler/record")
public class SystemCallbackHandlerLogController {

    private final CallbackHandlerLogService callbackHandlerLogService;

    /**
     * 获取支付接口列表
     */
    @PostMapping("/page")
    public PageResponse<CallbackHandlerLogVO> page(@RequestBody @Validated CallbackHandlerLogDTO pageDTO) {
        return callbackHandlerLogService.callbackHandlerPage(pageDTO);
    }


    /**
     * 获取回调记录详情
     */
    @GetMapping("/{id}")
    public CallbackHandlerLogDetailVO detail(@PathVariable Long id) {
        return callbackHandlerLogService.detail(id);
    }


}
