package com.baosight.payment.controller;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.pojo.dto.IsvPageDTO;
import com.baosight.payment.pojo.vo.PayIsvInfoVO;
import com.baosight.payment.pojo.vo.PayIsvPageVO;
import com.baosight.payment.service.PayIsvInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.baosight.saas.constant.BaseUrlConstant.SYSTEM;

/**
 * 服务商管理
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(SYSTEM + "/pm/pay/isv/manage")
public class PayIsvInfoController {

    private final PayIsvInfoService payIsvInfoService;

    /**
     * 获取服务商列表
     *
     * @param isvPageDTO 服务商列表请求体
     */
    @PostMapping("/page")
    public PageResponse<PayIsvPageVO> isvPage(@RequestBody IsvPageDTO isvPageDTO) {
        return payIsvInfoService.isvPage(isvPageDTO);
    }

    /**
     * 获取服务商详情信息
     */
    @GetMapping("/{id}")
    public PayIsvInfoVO info(@PathVariable("id") Long id) {
        return payIsvInfoService.info(id);
    }
}
