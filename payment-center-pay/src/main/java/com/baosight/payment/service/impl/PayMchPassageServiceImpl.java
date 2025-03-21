//package com.baosight.payment.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.baosight.payment.api.PayInterfaceApi;
//import com.baosight.payment.mapper.PayMchPassageMapper;
//import com.baosight.payment.pojo.entity.PayMchPassage;
////import com.baosight.payment.service.PayMchPassageService;
//import com.baosight.payment.vo.PayInterfaceVO;
//import com.baosight.saas.enums.Status;
//import lombok.RequiredArgsConstructor;
//import org.springframework.util.CollectionUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
///**
// * @author longjiangran
// * @description 针对表【pay_mch_passage(商户支付通道表)】的数据库操作Service实现
// * @createDate 2025-03-08 13:49:12
// */
////@Service
//@RequiredArgsConstructor
//public class PayMchPassageServiceImpl extends ServiceImpl<PayMchPassageMapper, PayMchPassage> implements PayMchPassageService {
//    private final PayMchPassageMapper payMchPassageMapper;
//    private final PayInterfaceApi payInterfaceApi;
//
//    /**
//     * 根据应用ID 和 支付方式， 查询出商户可用的支付接口
//     *
//     * @param mchNo   商户ID
//     * @param appId   应用id
//     * @param wayCode 支付方式
//     */
//    @Override
//    public PayMchPassage findMchPayPassage(Long mchNo, Long appId, String wayCode) {
//        List<PayMchPassage> payMchPassageList = payMchPassageMapper.selectList(new LambdaQueryWrapper<PayMchPassage>()
//                .eq(PayMchPassage::getMchNo, mchNo)
//                .eq(PayMchPassage::getAppId, appId)
//                .eq(PayMchPassage::getPayWayCode, wayCode)
//                .eq(PayMchPassage::getState, Status.NORMAL.getCode())
//        );
//        if (payMchPassageList.isEmpty()) {
//            return null;
//        } else {
//            // 返回一个可用通道
//            Map<Long, PayMchPassage> mchPayPassageMap = payMchPassageList.stream().collect(Collectors.toMap(PayMchPassage::getInterfaceId, e -> e));
//            List<PayInterfaceVO> payInterfaceVOList = payInterfaceApi.interfaceDefineAll(new ArrayList<>(mchPayPassageMap.keySet()));
//            if (!CollectionUtils.isEmpty(payInterfaceVOList)) {
//                PayInterfaceVO payInterface = payInterfaceVOList.stream().findFirst().orElse(null);
//                return mchPayPassageMap.get(payInterface.getId());
//
//            }
//        }
//        return null;
//    }
//}
//
//
//
//
