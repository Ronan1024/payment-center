package com.baosight.payment.system.controller.system;

import com.baosight.payment.system.pojo.dto.CreateAppDTO;
import com.baosight.payment.system.pojo.dto.MchAppListDTO;
import com.baosight.payment.system.pojo.vo.MchAppListVO;
import com.baosight.payment.system.pojo.vo.MchPayAppInfoVO;
import com.baosight.payment.system.service.PayMchAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.baosight.saas.constant.BaseUrlConstant.SYSTEM;

/**
 * @author L.J.Ran
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(SYSTEM + "/mch/app/manage")
public class SystemMchPayAppController {

    private final PayMchAppService payMchAppService;


    /**
     * 获取商家应用列表
     *
     * @param mchAppListDTO 商家应用列表查询参数
     * @param mchId         商家id
     */
    @PostMapping("/page/{mchId}")
    public List<MchAppListVO> machAppList(@RequestBody @Validated MchAppListDTO mchAppListDTO, @PathVariable("mchId") Long mchId) {
        return payMchAppService.machAppList(mchId, mchAppListDTO);
    }

    /**
     * 创建应用id
     */
    @PostMapping("/{mchId}")
    public Boolean createOrUpdate(@PathVariable("mchId") Long mchId, @RequestBody @Validated CreateAppDTO createAppDTO) {
        Long appId = payMchAppService.createOrUpdate(createAppDTO, mchId);
        return !ObjectUtils.isEmpty(appId);
    }


    /**
     * 获取商户应用信息
     *
     * @param id 应用id
     */
    @GetMapping("/{id}")
    public MchPayAppInfoVO info(@PathVariable("id") Long id) {
        return payMchAppService.info(id);
    }


    /**
     * saas 获取商户应用配置详情 临时使用后期进行删除剥离
     *
     * @param appId saas应用id
     */
    @GetMapping("/saas/{appId}")
    public MchPayAppInfoVO appInfoBySaasAppId(@PathVariable("appId") Integer appId) {
        return payMchAppService.appInfoBySaasAppId(appId);
    }


    /**
     * saas 创建商户应用
     *
     * @param create 创建应用请求体
     * @param appId  appId
     */
    @PostMapping("/saas/{appId}")
    public Boolean saasCreateOrUpdate(@RequestBody @Validated CreateAppDTO create, @PathVariable("appId") Integer appId) {
        return payMchAppService.saasCreateOrUpdate(create, appId);
    }


}
