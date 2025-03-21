//package com.baosight.payment.mch.controller;
//
//import com.baosight.database.page.PageResponse;
//import com.baosight.payment.mch.pojo.dto.MchAppPageDTO;
//import com.baosight.payment.mch.pojo.vo.MchAppVO;
//import com.baosight.payment.mch.service.PayMchAppService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import static com.baosight.saas.constant.BaseUrlConstant.SYSTEM;
//
///**
// * @program: payment-center
// * @description: 平台管理商户应用
// * @author: L.J.Ran
// * @create: 2025/3/17
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(SYSTEM + "/pm/pay/mch/app")
//public class SystemMchAppController {
//    private final PayMchAppService payMchAppService;
//
//    @PostMapping("/page")
//    public PageResponse<MchAppVO> mchAppPage(@RequestBody @Validated MchAppPageDTO mchAppPage) {
////        return payMchAppService.mchAppPage();
//        return null;
//    }
//}
