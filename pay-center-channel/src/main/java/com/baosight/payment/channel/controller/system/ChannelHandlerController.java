package com.baosight.payment.channel.controller.system;

import com.baosight.payment.channel.pojo.dto.resp.ChannelHandlerInfoRespDTO;
import com.baosight.payment.channel.pojo.dto.resp.ChannelHandlerListRespDTO;
import com.baosight.payment.channel.service.ChannelHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 运营端渠道处理器管理。
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/6/10
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/channel/handler/manager")
public class ChannelHandlerController {

    private final ChannelHandlerService channelHandlerService;


    /**
     * 渠道处理器列表
     *
     */
    @GetMapping("/list")
    public List<ChannelHandlerListRespDTO> list() {
        return channelHandlerService.list();
    }

    /**
     * 查询渠道处理器详情。
     */
    @GetMapping("/{handlerNo}")
    public ChannelHandlerInfoRespDTO info(@PathVariable String handlerNo) {
        return channelHandlerService.info(handlerNo);
    }
}
