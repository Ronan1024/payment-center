package com.baosight.payment.system.controller.system;


import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.system.pojo.dto.PayInterfaceTypeDTO;
import com.baosight.payment.system.pojo.dto.PayInterfaceTypePageDTO;
import com.baosight.payment.system.pojo.vo.PayInterfaceTypeVO;
import com.baosight.payment.system.service.PayInterfaceTypeService;
import com.baosight.security.annotation.AllowAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 接口类型管理
 *
 *
 * @author zhuzhuangzhi
 * @since 2025-12-16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/pm/pay/interface/type")
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
     * 获取接口类型列表
     * （新建接口页面使用）
     */
    @PostMapping("/list")
    public List<PayInterfaceTypeVO> list() {
        return payInterfaceTypeService.payInterfaceTypeList();
    }


    /**
     * 新增接口分类类型
     */
    @PostMapping
    public Boolean savePayInterfaceType(@RequestBody PayInterfaceTypeDTO PayInterfaceTypeDTO) {
        return payInterfaceTypeService.savePayInterfaceType(PayInterfaceTypeDTO);
    }



    /**
     * 修改接口分类类型
     */
    @PutMapping
    public Boolean updatePayInterfaceType(@RequestBody PayInterfaceTypeDTO PayInterfaceTypeDTO) {
        return payInterfaceTypeService.updatePayInterfaceType(PayInterfaceTypeDTO);
    }



    /**
     * 删除接口分类类型
     */
    @DeleteMapping("/{id}")
    public Boolean deletePayInterfaceType(@PathVariable("id") Long id) {
        return payInterfaceTypeService.deletePayInterfaceType(id);
    }



    /**
     * 根据ID获取接口分类类型详情
     */
    @GetMapping("/{id}")
    public PayInterfaceTypeVO getInfoById(@PathVariable("id") Long id) {
        return payInterfaceTypeService.getInfoById(id);
    }
}

