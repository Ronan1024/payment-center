package com.baosight.payment.mch.controller.system;


import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.mch.pojo.dto.MchInfoDTO;
import com.baosight.payment.mch.pojo.dto.MchPageDTO;
import com.baosight.payment.mch.pojo.vo.PayMchInfoVO;
import com.baosight.payment.mch.pojo.vo.PayMchListVO;
import com.baosight.payment.mch.service.PayMchInfoService;
import com.baosight.security.annotation.AllowAccess;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 商户管理
 *
 * @program: payment-center
 * @description: 系统商户管理控制器
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping( "/pm/pay/mch/manage")
@AllowAccess
public class SystemPayMchInfoController {

    @Resource
    private PayMchInfoService payMchInfoService;

    /**
     * 获取商户列表
     *
     * @param mchPage 商户列表请求体
     */
    @PostMapping("/page")
    public PageResponse<PayMchListVO> mchPage(@RequestBody @Validated MchPageDTO mchPage) {
        return payMchInfoService.mchPage(mchPage);
    }


    /**
     * 创建商户信息
     *
     * @param mchInfoDTO 创建商户信息请求体
     */
    @PostMapping
    public Boolean createMch(@RequestBody @Validated MchInfoDTO mchInfoDTO) {
        return payMchInfoService.createMch(mchInfoDTO);
    }


    /**
     * 根据租户ID获取租户相关的企业及法人信息
     *
     * @param id 商户id
     * @return 商户信息
     */
    @GetMapping("/byTenant/{tenantId}")
    public PayMchInfoVO tenantMchInfo(@PathVariable("tenantId") Long id) {
        return payMchInfoService.tenantMchInfo(id);
    }

    /**
     * 获取商户信息
     *
     * @param id 商户id
     * @return 商户信息
     */
    @GetMapping("/{id}")
    public PayMchInfoVO info(@PathVariable("id") Long id) {
        return payMchInfoService.info(id);
    }


    /**
     * 更新商户信息
     *
     * @param id         id
     * @param mchInfoDTO 商户信息
     */
    @PutMapping("/{id}")
    public Boolean updateMch(@PathVariable("id") Long id, @RequestBody @Validated MchInfoDTO mchInfoDTO) {
        return payMchInfoService.updateMch(id, mchInfoDTO);
    }
}
