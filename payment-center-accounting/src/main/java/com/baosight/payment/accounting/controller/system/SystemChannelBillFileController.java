package com.baosight.payment.accounting.controller.system;

import com.baosight.database.page.PageResponse;
import com.baosight.payment.accounting.pojo.dto.ChannelBillFilePageDTO;
import com.baosight.payment.accounting.pojo.vo.ChannelBillFileVO;
import com.baosight.payment.accounting.service.app.ChannelBillFileAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.baosight.saas.constant.BaseUrlConstant.SYSTEM;

/**
 * 系统渠道账单文件管理
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/7
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(SYSTEM + "/channel/bill/file")
public class SystemChannelBillFileController {
    private final ChannelBillFileAppService channelBillFileAppService;
    /**
     * 获取渠道账单文件列表
     * @param channelBillFilePage 渠道账单列表
     */
    @PostMapping("/page")
    public PageResponse<ChannelBillFileVO> channelBillPage(@RequestBody @Validated ChannelBillFilePageDTO channelBillFilePage){
       return  channelBillFileAppService.channelBillPage(channelBillFilePage);
    }


    /**
     * 获取渠道账单文件详情
     * @param id 渠道账单文件id
     */
    @GetMapping("/{id}")
    public ChannelBillFileVO channelBillFileInfo(@PathVariable Long id){
        return channelBillFileAppService.channelBillFileInfo(id);
    }
}
