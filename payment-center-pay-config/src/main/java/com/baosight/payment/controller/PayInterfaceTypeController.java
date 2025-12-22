package com.baosight.payment.controller;


import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.pojo.dto.PayInterfaceTypeDTO;
import com.baosight.payment.pojo.dto.PayInterfaceTypePageDTO;
import com.baosight.payment.pojo.vo.PayInterfaceTypeVO;
import com.baosight.payment.service.PayInterfaceTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 接口类型配置表 前端控制器
 * </p>
 *
 * @author zhuzhuangzhi
 * @since 2025-12-16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pm/pay/interfacetype")
public class PayInterfaceTypeController {

    private final PayInterfaceTypeService payInterfaceTypeService;


    /**
     * 获取接口类型分页列表
     */
    @PostMapping("/page")
    public PageResponse<PayInterfaceTypeVO> page(@RequestBody PayInterfaceTypePageDTO pageDTO) {
        return payInterfaceTypeService.payInterfaceTypePage(pageDTO);
    }



    /**
     * 新增接口分类类型
     */
    @PostMapping("/save")
    public Boolean savePayInterfaceType(@RequestBody PayInterfaceTypeDTO PayInterfaceTypeDTO) {
        return payInterfaceTypeService.savePayInterfaceType(PayInterfaceTypeDTO);
    }



    /**
     * 修改接口分类类型
     */
    @PostMapping("/update")
    public Boolean updatePayInterfaceType(@RequestBody PayInterfaceTypeDTO PayInterfaceTypeDTO) {
        return payInterfaceTypeService.updatePayInterfaceType(PayInterfaceTypeDTO);
    }



    /**
     * 删除接口分类类型
     */
    @GetMapping("/delete")
    public Boolean deletePayInterfaceType(@RequestParam("id") Long id) {
        return payInterfaceTypeService.deletePayInterfaceType(id);
    }



    /**
     * 根据ID获取接口分类类型详情
     */
    @GetMapping("/getInfoById")
    public PayInterfaceTypeVO getInfoById(@RequestParam("id") Long id) {
        return payInterfaceTypeService.getInfoById(id);
    }
}

