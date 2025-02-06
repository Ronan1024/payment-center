package com.baosight.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.pojo.dto.PayInterFaceDefineDTO;
import com.baosight.payment.pojo.dto.PayInterfacePageDTO;
import com.baosight.payment.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.pojo.vo.PayInterfaceDefineVO;

import java.util.List;

/**
 * @author longjiangran
 * @description 针对表【pay_interface_define(支付接口定义表)】的数据库操作Service
 * @createDate 2025-01-17 16:12:49
 */
public interface PayInterfaceDefineService extends IService<PayInterfaceDefine> {

    /**
     * 新增支付接口参数配置
     *
     * @param payInterFaceDefine 支付接口配置
     */
    Boolean insert(PayInterFaceDefineDTO payInterFaceDefine);

    /**
     * 更新支付接口
     *
     * @param id                    支付接口id
     * @param payInterFaceDefineDTO 支付接口更新信息
     */
    Boolean updatePayInterface(Long id, PayInterFaceDefineDTO payInterFaceDefineDTO);

    /**
     * 获取支付接口列表
     */
    List<PayInterfaceDefineListVO> payInterfacePage(PayInterfacePageDTO pageDTO);

    /**
     * 获取支付接口定义详情
     */
    PayInterfaceDefineVO detail(Long id);

    /**
     * 删除支付接口定义信息
     */
    Boolean delete(Long id);

    /**
     * 获取支付接口指定客户端列表信息
     *
     * @param payClientType 客户端常量
     * @param clientInfoId  客户端信息id
     */
    List<PayInterfaceDefineListVO> getPayInterfaceDefineList(PayClientType payClientType, Long clientInfoId);
}
