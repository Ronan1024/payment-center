//package com.baosight.payment.controller.system;
//
//import com.baosight.payment.adapter.enums.AdapterOperation;
//import com.baosight.payment.adapter.model.*;
//import com.baosight.payment.adapter.pojo.entity.PayChannelAdapterControl;
//import com.baosight.payment.adapter.pojo.entity.PayChannelAdapterOperationLog;
//import com.baosight.payment.adapter.service.AdapterAvailabilityChecker;
//import com.baosight.payment.adapter.service.AdapterRuntimeControlService;
//import com.baosight.payment.adapter.spi.PayChannelAdapterRegistry;
//import lombok.RequiredArgsConstructor;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 运营端适配器能力管理接口。
// *
// * <p>当前阶段仅提供能力查询、分组查询和 adapterKey 校验，不提供新增、编辑、删除和运行控制写操作。</p>
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/admin/channel/adapter")
//public class SystemChannelAdapterController {
//
//    private final PayChannelAdapterRegistry adapterRegistry;
//    private final AdapterAvailabilityChecker adapterAvailabilityChecker;
//    private final AdapterRuntimeControlService adapterRuntimeControlService;
//
//    /**
//     * 查询已注册适配器能力列表。
//     */
//    @GetMapping("/capabilities")
//    public List<AdapterCapability> capabilities(AdapterCapabilityQuery query) {
//        return adapterRegistry.listCapabilities(query);
//    }
//
//    /**
//     * 查询适配器分组能力摘要。
//     */
//    @GetMapping("/groups")
//    public List<AdapterGroupCapability> groups() {
//        return adapterRegistry.listGroups();
//    }
//
//    /**
//     * 校验渠道配置中的 adapterKey 是否能映射到后端已注册能力。
//     */
//    @PostMapping("/validate")
//    public AdapterValidateResult validate(@RequestBody AdapterValidateRequest request) {
//        List<String> errors = new ArrayList<>();
//        if (!StringUtils.hasText(request.getAdapterKey())) {
//            errors.add("adapterKey 不能为空");
//            return AdapterValidateResult.fail(request.getAdapterKey(), errors);
//        }
//        adapterRegistry.findCapability(request.getAdapterKey()).ifPresentOrElse(capability -> {
//            validateAdapterKeyParts(request, capability, errors);
//            if (request.getRequiredCapabilities() != null) {
//                validateRequiredCapabilities(request.getRequiredCapabilities(), capability, errors);
//            }
//            validateOperation(request.getAdapterKey(), request.getOperation(), errors);
//        }, () -> errors.add("adapterKey 不存在"));
//        if (errors.isEmpty()) {
//            return AdapterValidateResult.success(request.getAdapterKey());
//        }
//        return AdapterValidateResult.fail(request.getAdapterKey(), errors);
//    }
//
//    /**
//     * 禁用适配器指定能力范围。
//     */
//    @PostMapping("/disable")
//    public PayChannelAdapterControl disable(@RequestBody AdapterDisableRequest request) {
//        AdapterCapability capability = adapterRegistry.getRequiredCapability(request.getAdapterKey());
//        return adapterRuntimeControlService.disable(request, capability);
//    }
//
//    /**
//     * 恢复启用适配器。
//     */
//    @PostMapping("/enable")
//    public PayChannelAdapterControl enable(@RequestBody AdapterEnableRequest request) {
//        AdapterCapability capability = adapterRegistry.getRequiredCapability(request.getAdapterKey());
//        return adapterRuntimeControlService.enable(request, capability);
//    }
//
//    /**
//     * 设置适配器维护状态。
//     */
//    @PostMapping("/maintenance")
//    public PayChannelAdapterControl maintenance(@RequestBody AdapterMaintenanceRequest request) {
//        AdapterCapability capability = adapterRegistry.getRequiredCapability(request.getAdapterKey());
//        return adapterRuntimeControlService.maintenance(request, capability);
//    }
//
//    /**
//     * 查询适配器运行控制操作日志。
//     */
//    @GetMapping("/operation-log")
//    public List<PayChannelAdapterOperationLog> operationLog(AdapterOperationLogQuery query) {
//        return adapterRuntimeControlService.operationLogs(query);
//    }
//
//    private void validateAdapterKeyParts(AdapterValidateRequest request, AdapterCapability capability, List<String> errors) {
//        if (StringUtils.hasText(request.getChannelCode()) && !request.getChannelCode().equals(capability.getChannelCode())) {
//            errors.add("adapterKey 解析出的 channelCode 与请求不一致");
//        }
//        if (StringUtils.hasText(request.getModeCode()) && !request.getModeCode().equals(capability.getModeCode())) {
//            errors.add("adapterKey 解析出的 modeCode 与请求不一致");
//        }
//        if (StringUtils.hasText(request.getInterfaceCode()) && !request.getInterfaceCode().equals(capability.getInterfaceCode())) {
//            errors.add("adapterKey 解析出的 interfaceCode 与请求不一致");
//        }
//    }
//
//    private void validateOperation(String adapterKey, AdapterOperation operation, List<String> errors) {
//        if (operation == null) {
//            return;
//        }
//        try {
//            adapterAvailabilityChecker.checkAvailable(adapterKey, operation);
//        } catch (RuntimeException ex) {
//            errors.add(ex.getMessage());
//        }
//    }
//
//    private void validateRequiredCapabilities(List<String> requiredCapabilities, AdapterCapability capability, List<String> errors) {
//        for (String requiredCapability : requiredCapabilities) {
//            switch (requiredCapability) {
//                case "PAY" -> addIfUnsupported(errors, capability.isSupportPay(), "适配器不支持支付");
//                case "QUERY" -> addIfUnsupported(errors, capability.isSupportQuery(), "适配器不支持查单");
//                case "CLOSE" -> addIfUnsupported(errors, capability.isSupportClose(), "适配器不支持关单");
//                case "REFUND" -> addIfUnsupported(errors, capability.isSupportRefund(), "适配器不支持退款");
//                case "REFUND_QUERY" -> addIfUnsupported(errors, capability.isSupportRefundQuery(), "适配器不支持退款查询");
//                case "NOTIFY" -> addIfUnsupported(errors, capability.isSupportPayNotify() || capability.isSupportRefundNotify(), "适配器不支持回调解析");
//                case "BILL" -> addIfUnsupported(errors, capability.isSupportDownloadBill(), "适配器不支持账单下载");
//                default -> errors.add("未知能力: " + requiredCapability);
//            }
//        }
//    }
//
//    private void addIfUnsupported(List<String> errors, boolean supported, String message) {
//        if (!supported) {
//            errors.add(message);
//        }
//    }
//}
