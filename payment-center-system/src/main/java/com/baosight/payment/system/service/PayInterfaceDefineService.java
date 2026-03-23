package com.baosight.payment.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baosight.database.core.page.PageResponse;
import com.baosight.payment.enums.PayClientType;
import com.baosight.payment.system.pojo.dto.PayInterfaceListDTO;
import com.baosight.payment.system.pojo.dto.req.PayInterFaceDefineReqDTO;
import com.baosight.payment.system.pojo.dto.resp.ClientPayChannelDefineRespDTO;
import com.baosight.payment.system.pojo.dto.resp.PayingChannelDefineListRespDTO;
import com.baosight.payment.system.pojo.entity.PayInterfaceDefine;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineListVO;
import com.baosight.payment.system.pojo.vo.PayInterfaceDefineVO;

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
    Boolean insert(PayInterFaceDefineReqDTO payInterFaceDefine);

    /**
     * 更新支付接口
     *
     * @param payInterFaceDefineDTO 支付接口更新信息
     * @param id                    支付接口id
     */
    Boolean updatePayInterface(PayInterFaceDefineReqDTO payInterFaceDefineDTO, Long id);

    /**
     * 获取支付接口列表
     */
    PageResponse<PayInterfaceDefineListVO> payInterfacePage(PayInterfaceListDTO pageDTO);


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
     */
    List<PayInterfaceDefineListVO> getPayInterfaceDefineList(PayClientType payClientType);


    /**
     * 获取支付接口指定客户端列表信息
     */
    List<PayInterfaceDefineListVO> getPayInterfaceDefineList(PayClientType payClientType, List<Long> interfaceIdList);

    /**
     * 获取服务商可以用的支付接口定义列表
     */
    List<PayInterfaceDefineListVO> getPayInterfaceDefineListByISV();

    /**
     * 获取支付接口定义信息
     *
     * @param id 支付接口id
     * @return 支付接口新信息
     */
    PayInterfaceDefine payInterfaceDefineBy(Long id);

    /**
     * 获取可配置的接口定义列表
     *
     * @param mchId 商户id
     */
    List<PayInterfaceDefineListVO> mchPayInterfaceDefineList(Long mchId);

    /**
     * 根据接口id 获取接口列表
     *
     * @param interfaceIdList 接口id
     */
    List<PayInterfaceDefine> payInterfaceDefineByIdList(List<Long> interfaceIdList);

    /**
     * 获取支付接口定义信息
     *
     * @param code 支付接口code
     */
    PayInterfaceDefine payInterfaceDefineByCode(String code);

    /**
     * 查询商户可以绑定的支付接口定义信息
     *
     * @param payClientType
     * @return
     */
    List<PayInterfaceDefineListVO> queryList(Integer payClientType);

    /**
     * 获取支付通道列表
     * @param mchType 商户类型
     */
    List<PayingChannelDefineListRespDTO> channelDefineList(Integer mchType);

    /**
     * 修改支付通道启用状态
     * @param id 支付通道id
     */
    void editEnable(Long id);

    /**
     * 获取客户端支付渠道配置信息
     *
     * @param clientId  客户端id
     * @param type      客户端类型
     * @param channelId 渠道id
     */
    ClientPayChannelDefineRespDTO clientChannelDefine(Long clientId, Integer type, Long channelId);
}
