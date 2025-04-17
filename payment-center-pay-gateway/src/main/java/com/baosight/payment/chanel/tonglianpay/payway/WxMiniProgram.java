package com.baosight.payment.chanel.tonglianpay.payway;

import com.baosight.common.exception.ServiceException;
import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.access.tl.model.TongLianIsvConfigDAO;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.api.MchChannelCorrelationApi;
import com.baosight.payment.chanel.IPaymentService;
import com.baosight.payment.chanel.tonglianpay.TongLianClient;
import com.baosight.payment.chanel.tonglianpay.response.TongLianResponse;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.dao.TongLianMchConfigDAO;
import com.baosight.payment.enums.ChannelState;
import com.baosight.payment.enums.ChannelType;
import com.baosight.payment.enums.PayInterfaceCode;
import com.baosight.payment.enums.PayWayCode;
import com.baosight.payment.model.order.UnifiedOrder;
import com.baosight.payment.order.api.dto.CreateOrderDTO;
import com.baosight.payment.pojo.dto.WxMiniProgramDTO;
import com.baosight.payment.pojo.vo.OrderChannelHandlerResult;
import com.baosight.payment.vo.MchChannelCorrelationVO;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.utils.json.JsonUtil;
import com.baosight.utils.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.baosight.payment.constant.PayWay.TONG_LIAN_WX_MINI_PROGRAM;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Slf4j
@RequiredArgsConstructor
@Component(value = TONG_LIAN_WX_MINI_PROGRAM)
public class WxMiniProgram implements IPaymentService {

    private final MchAppConfigApi mchAppConfigApi;

    private final MchChannelCorrelationApi mchChannelCorrelationApi;

    @Value("${pay.notifyUrl}")
    private String notifyUrl;

    @Value("${pay.tl_url}")
    private String tlUrl;

    /**
     * 获取到接口code
     **/
    @Override
    public String getPayInterfaceCode() {
        return PayInterfaceCode.TONG_LIAN_PAY.getCode();
    }


    /**
     * 调起支付接口并响应数据
     *
     * @param unifiedOrder 申请支付请求体
     * @param mchInfo      商户信息
     * @param createOrder
     */
    @Override
    public OrderChannelHandlerResult pay(UnifiedOrder unifiedOrder, MchInfoVO mchInfo, CreateOrderDTO createOrder) {
        log.info("获取到请求参数：{}", unifiedOrder);
        TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfigDAO = mchAppConfigApi.tongLianIsvAndMchConfig(mchInfo.getId(), createOrder.getIfCode(), mchInfo.getIsvId());

        WxMiniProgramDTO wxMiniProgramDTO = JsonUtil.parse(unifiedOrder.getChannelExtra(), WxMiniProgramDTO.class);
        MchChannelCorrelationVO mchChannelCorrelationVO = mchChannelCorrelationApi.mchChannelCorrelation(mchInfo.getId(), ChannelType.WECHAT.getCode());
        TongLianMchConfigDAO mchConfig = tongLianIsvAndMchConfigDAO.mchConfig();
        TongLianIsvConfigDAO isvConfig = tongLianIsvAndMchConfigDAO.isvConfig();
        Map<String, Object> payModel = new HashMap<>();
        Map<String, Object> wxMiniProgram = new HashMap<>();
        wxMiniProgram.put("vspCusid", mchConfig.getSignNum());
        wxMiniProgram.put("acct", wxMiniProgramDTO.getOpenId());
        wxMiniProgram.put("subAppid", mchChannelCorrelationVO.getChannelId());
        payModel.put("WECHATPAY_MINIPROGRAM", wxMiniProgram);

        Map<String, Object> map = new HashMap<>();
        // 用户id
        map.put("signNum", unifiedOrder.getSignUser());
        // 商户订单号
        map.put("reqTraceNum", createOrder.getOrderId());
        //订单金额=支付金额+营销金额
        map.put("orderAmount", unifiedOrder.getTotalAmount());
        //支付金额
        map.put("payAmount", unifiedOrder.getPayAmount());
        //营销金额
        map.put("promotionAmount", unifiedOrder.getPromotionAmount());
        map.put("payMode", payModel);
        map.put("respUrl", notifyUrl + "/notice/api/pay/notify/" + getPayInterfaceCode());
        map.put("receiverSignNum", mchInfo.getId());
        map.put("goodsName", unifiedOrder.getSubject());
        map.put("goodsDesc", unifiedOrder.getBody());
        OrderChannelHandlerResult result = new OrderChannelHandlerResult();
        try {
            // 获取用户信息
            TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "2085", map);
            TongLianClient tongLianClient = new TongLianClient(isvConfig);
            TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, tlUrl);
            // TODO 记录调用信息
            log.info("通联支付响应: {}", response);
            if (Boolean.TRUE.equals(response.getSuccess())) {
                // 支付中
                result.setChannelState(ChannelState.PROCESSING.getCode());
                result.setChannelAttach(response.getResult().toString());
                result.setChannelOrderNo(response.get("respTraceNum").asText());
                result.setChannelMchNo(mchConfig.getSignNum());
                TongLianResponse tongLianResponse = new TongLianResponse();
                JsonNode frontParamInfo = JsonUtil.readTree(response.get("chnlFrontParamInfo").asText());
                tongLianResponse.setUrl(frontParamInfo.get("chnlPayInfo").asText());
                result.setResponse(tongLianResponse);
            } else {
            }
        } catch (Exception e) {
            result.setChannelState(ChannelState.SYSTEM_ERROR.getCode());
        }
        return result;
    }

    /**
     * 前置检查如参数等信息是否符合要求， 返回错误信息或直接抛出异常即可
     *
     * @param unifiedOrder
     * @param payOrder
     */
    @Override
    public ServiceException preCheck(UnifiedOrder unifiedOrder, CreateOrderDTO payOrder) {
        try {
            log.info("获取到的参数：{}", unifiedOrder.getChannelExtra());
            WxMiniProgramDTO wxMiniProgramDTO = JsonUtil.parse(unifiedOrder.getChannelExtra(), WxMiniProgramDTO.class);
            if (ObjectUtils.isEmpty(wxMiniProgramDTO.getOpenId())) {
                return new ServiceException("openId不能为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 自定义支付订单号， 若返回空则使用系统生成订单号
     *
     * @param unifiedOrder
     * @param payOrder
     * @param mchInfoVO
     */
    @Override
    public String customPayOrderId(UnifiedOrder unifiedOrder, CreateOrderDTO payOrder, MchInfoVO mchInfoVO) {
        return "";
    }

    /**
     * 获取到的支付方式code
     */
    @Override
    public String payWayCode() {
        return PayWayCode.TONG_LIAN_WX_MINI_PROGRAM.getCode();
    }

    /**
     * 是否支持当前支付方式
     *
     * @param patWayCode 支付方式code
     */
    @Override
    public boolean isSupport(String patWayCode) {
        return false;
    }
}
