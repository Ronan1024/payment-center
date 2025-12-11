package com.baosight.payment.check.controller;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.check.error.BillError;
import com.baosight.payment.check.pojo.dto.ChannelBillDTO;
import com.baosight.payment.check.pojo.vo.ChannelBillListVO;
import com.baosight.payment.check.service.app.ChannelBillAppService;
import com.baosight.utils.utils.Assert;
import com.baosight.utils.utils.ObjectUtils;
import com.baosight.web.core.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 渠道账单列表
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
@RequiredArgsConstructor
@RestController
//@RequestMapping(SYSTEM + "/channel/bill")
@RequestMapping( "/channel/bill")
public class SystemChannelBillController {

    private final ChannelBillAppService channelBillAppService;


    /**
     * 渠道账单列表
     * @param channelBill 渠道账单请求信息
     */
    @PostMapping("/page")
    public PageResponse<ChannelBillListVO> page(@RequestBody @Validated ChannelBillDTO channelBill) {
        Assert.isTrue(ObjectUtils.isEmpty(channelBill.getBillFileId()) || ObjectUtils.isEmpty(channelBill.getBillFileCode()), ApiException.supplier(BillError.CHANNEL_FILE_OR_CODE_NULL));
        return channelBillAppService.channelBillPage(channelBill);
    }
}
