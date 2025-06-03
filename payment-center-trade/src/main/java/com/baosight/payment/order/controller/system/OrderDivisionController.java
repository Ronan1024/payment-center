package com.baosight.payment.order.controller.system;

import com.baosight.database.page.PageRequest;
import com.baosight.database.page.PageResponse;
import com.baosight.payment.order.pojo.vo.DivisionBatchPageVO;
import com.baosight.payment.order.pojo.vo.DivisionRecordPageVO;
import com.baosight.payment.order.service.OrderDivisionBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.baosight.saas.constant.BaseUrlConstant.SYSTEM;

/**
 * 分账批次管理
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/9
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(SYSTEM + "/settlement/division/batch/manage")
public class OrderDivisionController {

    private final OrderDivisionBatchService orderDivisionBatchService;

    @PostMapping("/page")
    public PageResponse<DivisionBatchPageVO> page(@RequestBody @Validated PageRequest pageRequest) {
        return orderDivisionBatchService.divisionBatchPage(pageRequest);
    }

    /**
     * 对账记录
     *
     * @param pageRequest
     * @return
     */
    @PostMapping("/record/{batchId}")
    public PageResponse<DivisionRecordPageVO> recordPage(@RequestBody @Validated PageRequest pageRequest, @PathVariable Long batchId) {
        return orderDivisionBatchService.recordPage(pageRequest, batchId);
    }


}
