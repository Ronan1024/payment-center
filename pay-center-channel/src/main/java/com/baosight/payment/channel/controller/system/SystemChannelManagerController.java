package com.baosight.payment.channel.controller.system;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.channel.pojo.dto.PayingChannelInfoDTO;
import com.baosight.payment.channel.pojo.dto.PayingChannelInfoPageDTO;
import com.baosight.payment.channel.pojo.resp.ChannelCodeRespDTO;
import com.baosight.payment.channel.pojo.vo.PayingChannelInfoVO;
import com.baosight.payment.channel.service.PayChannelService;
import com.baosight.payment.channel.service.PayingChannelInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运营端渠道管理
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/channel/manager")
public class SystemChannelManagerController {

    private final PayChannelService payChannelService;
    private final PayingChannelInfoService payingChannelInfoService;

    /**
     * 获取所有的渠道
     */
    @GetMapping("/list")
    public List<ChannelCodeRespDTO> list() {
        return payChannelService.list();
    }

    /**
     * 分页查询支付渠道信息
     */
    @PostMapping("/page")
    public PageResponse<PayingChannelInfoVO> page(@RequestBody PayingChannelInfoPageDTO pageDTO) {
        return payingChannelInfoService.page(pageDTO);
    }

    /**
     * 新增支付渠道信息
     */
    @PostMapping
    public Boolean save(@RequestBody @Validated PayingChannelInfoDTO dto) {
        return payingChannelInfoService.savePayingChannelInfo(dto);
    }

    /**
     * 更新支付渠道信息
     */
    @PutMapping
    public Boolean update(@RequestBody @Validated PayingChannelInfoDTO dto) {
        return payingChannelInfoService.updatePayingChannelInfo(dto);
    }

    /**
     * 删除支付渠道信息
     */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        return payingChannelInfoService.deletePayingChannelInfo(id);
    }

    /**
     * 根据ID获取支付渠道信息详情
     */
    @GetMapping("/{id}")
    public PayingChannelInfoVO getInfoById(@PathVariable("id") Long id) {
        return payingChannelInfoService.getInfoById(id);
    }
}
