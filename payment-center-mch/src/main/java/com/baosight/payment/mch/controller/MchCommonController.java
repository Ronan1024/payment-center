package com.baosight.payment.mch.controller;

import com.baosight.payment.enums.MchType;
import com.baosight.payment.mch.pojo.vo.MchTypeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @program: payment-center
 * @description: 商户通用控制器
 * @author: L.J.Ran
 * @create: 2025/3/17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/mch/common/")
public class MchCommonController {


    /**
     * 获取商户类型列表
     * @return 商户类型列表
     */
    @GetMapping("/type/list")
    public List<MchTypeVO> mchTypeList() {
        return Arrays.stream(MchType.values()).map(e -> new MchTypeVO(e.getCode(), e.name())).toList();
    }
}
