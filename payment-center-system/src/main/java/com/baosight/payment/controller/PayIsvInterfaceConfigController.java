package com.baosight.payment.controller;

import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.pojo.entity.PayInterfaceConfig;
import com.baosight.payment.pojo.vo.PayInterfaceConfigVO;
import com.baosight.payment.service.PayInterfaceConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.naming.ldap.PagedResultsControl;
import java.math.BigDecimal;

import static com.baosight.saas.constant.BaseUrlConstant.SYSTEM;

@RestController
@RequiredArgsConstructor
@RequestMapping(SYSTEM + "/pm/pay/isv/config/manage")
public class PayIsvInterfaceConfigController {
    private final PayInterfaceConfigService payInterfaceConfigService;

//    /**
//     * @Author: ZhuXiao
//     * @Description: 根据 服务商号、接口类型 获取商户参数配置
//     * @Date: 17:03 2021/4/27
//     */
//    @Operation(summary = "根据[服务商号]、[接口类型]获取商户参数配置", description = "")
//    @Parameters({
//            @Parameter(name = "iToken", description = "用户身份凭证", required = true, in = ParameterIn.HEADER),
//            @Parameter(name = "isvNo", description = "服务商号", required = true),
//            @Parameter(name = "ifCode", description = "接口类型代码", required = true)
//    })

    /**
     * 获取服务支付接口配置信息
     *
     * @param id          服务商id
     * @param interfaceId 支付接口id
     * @return 服务商支付接口配置详情
     */
    @GetMapping("/{id}/{interfaceId}")
    public PayInterfaceConfigVO getIsvConfigInfo(@PathVariable(value = "id") Long id, @PathVariable(value = "interfaceId") Long interfaceId) {
        return payInterfaceConfigService.getIsvConfigInfo(PayClientType.SERVICE_PROVIDER, id, interfaceId);
    }



    /**
     * @Author: ZhuXiao
     * @Description: 服务商支付接口参数配置
     * @Date: 16:45 2021/4/27
     */
    @Operation(summary = "服务商支付接口参数配置", description = "")
    @Parameters({
            @Parameter(name = "iToken", description = "用户身份凭证", required = true, in = ParameterIn.HEADER),
            @Parameter(name = "infoId", description = "服务商号", required = true),
            @Parameter(name = "ifCode", description = "接口类型代码", required = true),
            @Parameter(name = "ifParams", description = "接口配置参数,json字符串"),
            @Parameter(name = "ifRate", description = "支付接口费率"),
            @Parameter(name = "remark", description = "备注"),
            @Parameter(name = "state", description = "状态: 0-停用, 1-启用")
    })
    @PreAuthorize("hasAuthority('ENT_ISV_PAY_CONFIG_ADD')")
    @PostMapping
    @MethodLog(remark = "更新服务商支付参数")
    public ApiRes saveOrUpdate() {

        String infoId = getValStringRequired("infoId");
        String ifCode = getValStringRequired("ifCode");

        PayInterfaceConfig payInterfaceConfig = getObject(PayInterfaceConfig.class);
        payInterfaceConfig.setInfoType(CS.INFO_TYPE_ISV);

        // 存入真实费率
        if (payInterfaceConfig.getIfRate() != null) {
            payInterfaceConfig.setIfRate(payInterfaceConfig.getIfRate().divide(new BigDecimal("100"), 6, BigDecimal.ROUND_HALF_UP));
        }

        //添加更新者信息
        Long userId = getCurrentUser().getSysUser().getSysUserId();
        String realName = getCurrentUser().getSysUser().getRealname();
        payInterfaceConfig.setUpdatedUid(userId);
        payInterfaceConfig.setUpdatedBy(realName);

        //根据 服务商号、接口类型 获取商户参数配置
        PayInterfaceConfig dbRecoed = payInterfaceConfigService.getByInfoIdAndIfCode(CS.INFO_TYPE_ISV, infoId, ifCode);
        //若配置存在，为saveOrUpdate添加ID，第一次配置添加创建者
        if (dbRecoed != null) {
            payInterfaceConfig.setId(dbRecoed.getId());

            // 合并支付参数
            payInterfaceConfig.setIfParams(StringKit.marge(dbRecoed.getIfParams(), payInterfaceConfig.getIfParams()));
        } else {
            payInterfaceConfig.setCreatedUid(userId);
            payInterfaceConfig.setCreatedBy(realName);
        }

        boolean result = payInterfaceConfigService.saveOrUpdate(payInterfaceConfig);
        if (!result) {
            return ApiRes.fail(ApiCodeEnum.SYSTEM_ERROR, "配置失败");
        }

        // 推送mq到目前节点进行更新数据
        mqSender.send(ResetIsvMchAppInfoConfigMQ.build(ResetIsvMchAppInfoConfigMQ.RESET_TYPE_ISV_INFO, infoId, null, null));

        return ApiRes.ok();
    }

}
