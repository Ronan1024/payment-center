package com.baosight.payment.accounting.controller.system;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.accounting.pojo.dto.CheckBatchPageDTO;
import com.baosight.payment.accounting.pojo.vo.CheckBatchListVO;
import com.baosight.payment.accounting.service.app.CheckBatchRecordAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.baosight.saas.constant.LoginType.SYSTEM;

/**
 * 系统对账批次管理
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/4
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(SYSTEM + "/check/batch/manage")
public class SystemCheckBatchController {
    private final CheckBatchRecordAppService checkBatchRecordAppService;


    /**
     * 获取系统对账批次管理
     */
    @PostMapping("/page")
    public PageResponse<CheckBatchListVO> page(@RequestBody @Validated CheckBatchPageDTO checkBatchPage) {
        return checkBatchRecordAppService.page(checkBatchPage);
    }


}
