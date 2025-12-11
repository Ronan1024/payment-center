package com.baosight.payment.isv.controller;


import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.isv.pojo.dto.CreateIsvDTO;
import com.baosight.payment.isv.pojo.dto.IsvPageDTO;
import com.baosight.payment.isv.pojo.vo.PayIsvInfoVO;
import com.baosight.payment.isv.pojo.vo.PayIsvPageVO;
import com.baosight.payment.isv.service.PayIsvInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author L.J.Ran
 */
@RestController
@RequiredArgsConstructor
//@RequestMapping(SYSTEM + "/pm/pay/isv/manage")
@RequestMapping("/pm/pay/isv/manage")
public class SystemPayIsvInfoController {
    private final PayIsvInfoService payIsvInfoService;


    /**
     * 获取服务商列表
     *
     * @param isvPageDTO 服务商列表请求体
     */
    @PostMapping("/page")
    public PageResponse<PayIsvPageVO> isvPage(@RequestBody @Validated IsvPageDTO isvPageDTO) {
        return payIsvInfoService.isvPage(isvPageDTO);
    }

    /**
     * 获取服务商详情信息
     */
//    @AllowAccess
    @GetMapping("/{id}")
    public PayIsvInfoVO info(@PathVariable("id") Long id) {
        return payIsvInfoService.info(id);
    }

    /**
     * 新增服务商
     */
    @PostMapping
    public Boolean createIsv(@RequestBody @Validated CreateIsvDTO createIsvDTO) {
        return payIsvInfoService.createIsv(createIsvDTO);
    }


    /**
     * 禁用｜启用 服务商
     *
     * @param isvId 服务商id
     */
    @PutMapping("/enable/{isvId}")
    public Boolean enable(@PathVariable("isvId") Long isvId) {
        return payIsvInfoService.enable(isvId);
    }

    /**
     * 获取所有服务商列表
     */
    @GetMapping("/list")
    public List<PayIsvPageVO> list() {
        return payIsvInfoService.isvList();
    }
}
