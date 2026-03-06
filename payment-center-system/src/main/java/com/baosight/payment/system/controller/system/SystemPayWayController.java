package com.baosight.payment.system.controller.system;

import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.api.MchInfoApi;
import com.baosight.payment.enums.PayingAgency;
import com.baosight.payment.enums.PayingClient;
import com.baosight.payment.error.MchError;
import com.baosight.payment.system.convert.PayWayConvert;
import com.baosight.payment.system.pojo.dto.PayWayPageDTO;
import com.baosight.payment.system.pojo.dto.SavePayWayDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.system.pojo.entity.PayWay;
import com.baosight.payment.system.pojo.vo.*;
import com.baosight.payment.system.service.PayInterfaceConfigService;
import com.baosight.payment.system.service.PayWayService;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.utils.utils.Assert;
import com.baosight.web.core.exception.ApiException;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * 支付方式控制台
 *
 * @author L.J.Ran
 */
@RestController
@RequiredArgsConstructor
@RequestMapping( "/pm/pay/way/manage")
public class SystemPayWayController {

    private final PayWayService payWayService;
    private final PayInterfaceConfigService payInterfaceConfigService;
    @Resource
    private MchInfoApi mchInfoApi;

    /**
     * 支付方式列表
     */
    @PostMapping("/page")
    public PageResponse<PayWayPageVO> page(@RequestBody @Validated PayWayPageDTO payWayDTO) {
        return payWayService.pagePayWay(payWayDTO);
    }


    /**
     * 支付方式列表
     * （新建接口页面使用）
     */
    @GetMapping("/list")
    public List<PayWayPageVO> list() {
        return payWayService.pagePayList();
    }

    /**
     * 获取支付方式的详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public PayWayVO detail(@PathVariable("id") Long id) {
        return payWayService.detailPayWay(id);
    }


    /**
     * 新增支付方式
     */
    @PostMapping
    public Boolean savePayWay(@RequestBody @Validated SavePayWayDTO payWayDTO) {
        return payWayService.savePayWay(payWayDTO);
    }


    /**
     * 更新支付方式
     * @param payWayDTO
     * @return
     */
    @PutMapping
    public Boolean update(@RequestBody @Validated SavePayWayDTO payWayDTO) {
        return payWayService.updatePayWay(payWayDTO);
    }

    /**
     * 删除支付方式
     */
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable("id") Long id) {
        return payWayService.deletePayWay(id);
    }


    /**
     * 禁用支付方式
     */
    @PutMapping("/disable/{id}")
    public Boolean disable(@PathVariable("id") Long id) {
        return payWayService.disablePayWay(id);
    }


    /**
     * 获取支付企业列表
     */
    @GetMapping("/pay_agency")
    public List<PayingAgencyVO> payAgency() {
        return Arrays.stream(PayingAgency.values()).map(e -> new PayingAgencyVO(e.code(), e.desc())).toList();
    }


    /**
     * 获取支付客户端
     */
    @GetMapping("/pay_client")
    public List<PayingClientVO> payingClient() {
        return Arrays.stream(PayingClient.values()).map(e -> new PayingClientVO(e.getCode(), e.getMsg())).toList();
    }


    /**
     * 获取商户可用的支付方式
     */
    @GetMapping("/mch/available/{mchId}")
    public List<PayWayListVO> mchAvailablePayWayList(@PathVariable("mchId") Long mchId) {
        MchInfoVO mchInfoVO = mchInfoApi.mchInfo(mchId);
        Assert.isNull(mchInfoVO, ApiException.supplier(MchError.MCH_NOT_FOUND));
        // 获取配置的可用支付方式列表
        List<PayInterfaceConfig> payInterfaceConfig = payInterfaceConfigService.getMchInterfaceConfig(mchId, mchInfoVO.getType());

        List<Long> payWayIdList = payInterfaceConfig.stream().map(e -> Arrays.asList(e.getPayWay().split(",")))
                .flatMap(Collection::stream)
                .map(Long::valueOf).toList();

        List<PayWay> payWayList = payWayService.getPayWayList(payWayIdList);
        return payWayList.stream().map(PayWayConvert.INSTANCE::toPayWayListVO).toList();
    }
}
