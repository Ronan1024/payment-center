package com.baosight.payment.chanel.tonglianpay;

import com.baosight.distributedid.toolkit.SnowflakeIdUtil;
import com.baosight.payment.api.MchAppConfigApi;
import com.baosight.payment.chanel.IRefundService;
import com.baosight.payment.dao.TongLianIsvAndMchConfigDAO;
import com.baosight.payment.enums.ChannelState;
import com.baosight.payment.enums.DivisionState;
import com.baosight.payment.enums.PayInterfaceCode;
import com.baosight.payment.exception.ChannelHandlerException;
import com.baosight.payment.model.refund.RefundOrder;
import com.baosight.payment.order.api.vo.CreateRefundOrderVO;
import com.baosight.payment.order.api.vo.OrderVO;
import com.baosight.payment.pojo.vo.RefundChannelHandlerResult;
import com.baosight.payment.vo.MchInfoVO;
import com.baosight.utils.json.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/29
 */
@Slf4j
@RequiredArgsConstructor
@Component("tLPayRefund")
public class TlPayRefundService implements IRefundService {
    private final MchAppConfigApi mchAppConfigApi;

    @Value("${pay.tl_url}")
    private String tlUrl;


    @Value("${pay.notifyUrl}")
    private String notifyUrl;

    /**
     * 获取到接口code
     **/
    @Override
    public String getInterfaceCode() {
        return PayInterfaceCode.TONG_LIAN_PAY.code();
    }

    /**
     * 前置检查如参数等信息是否符合要求， 返回错误信息或直接抛出异常即可
     *
     * @param refundOrder
     * @param payOrder
     */
    @Override
    public String preCheck(RefundOrder refundOrder, OrderVO payOrder) {
        return "";
    }

    /**
     * 调起退款接口，并响应数据；  内部处理普通商户和服务商模式
     *
     * @param refundOrder
     * @param createRefundOrderVO
     * @param payOrder
     * @param mchInfo
     */
    @Override
    public RefundChannelHandlerResult refund(RefundOrder refundOrder, CreateRefundOrderVO createRefundOrderVO, OrderVO payOrder, MchInfoVO mchInfo) {
        RefundChannelHandlerResult handlerResult = new RefundChannelHandlerResult();

        try {
            TongLianIsvAndMchConfigDAO tongLianIsvAndMchConfig = mchAppConfigApi.tongLianIsvAndMchConfig(mchInfo.getId(), payOrder.getIfCode(), mchInfo.getIsvId());

            Map<String, Object> map = new HashMap<>();
            // 商户退款订单号
            map.put("reqTraceNum", createRefundOrderVO.getRefundOrderId());
            //原消费订单
            map.put("orgRespTraceNum", payOrder.getChannelOrderNo());
            //当上送“渠道退款金额”时，该字段与“渠道退款金额”+”营销退款金额“一致
            //当不上送“渠道退款金额”时，该字段与“资金确认退款金额”或”营销退款金额“的最大值保持一致
            // 处理分账前退款与分账后退款
            Map<String, Object> sepRefundInfo = new HashMap<>();
            Long orderAmount = 0L;
            if (Boolean.TRUE.equals(payOrder.getHasDivision()) && payOrder.getDivisionState().equals(DivisionState.DIVISION_ING.code())) {
                // 分账退款
                BigDecimal bigDecimal = new BigDecimal(10);
                BigDecimal divide = new BigDecimal(120).divide(new BigDecimal(10000), 3, RoundingMode.HALF_UP);
                BigDecimal multiply = bigDecimal.multiply(divide);
                long longValue = multiply.multiply(new BigDecimal(100)).longValue();
            } else {
                // 普通退款
                Long promotionAmount = Long.valueOf(refundOrder.getPromotionAmount());
                Long refundAmount = Long.valueOf(refundOrder.getRefundAmount());
                orderAmount = refundAmount + promotionAmount;
            }
            map.put("orderAmount", orderAmount);
            sepRefundInfo.put("cnlRefundAmount", orderAmount);
            sepRefundInfo.put("signNum", mchInfo.getId());

            // 退款总金额 单位：分。
            //
            //【消费申请】退款，
            //
            //。
            //
            //当不上送“渠道退款金额”时，该字段与“资金确认退款金额”或”营销退款金额“的最大值保持一致


            // 营销金额单位：分
            //
            //1、不能超过退款总金额
            //
            //2、不能超过原订单营销金额
            //
            //3、支持部分退款
//        map.put("promotionAmount", promotionAmount);
            //订单退款详情


//        sepRefundInfo.put("orderAmount", orderAmount);
            // 需要配置平台抽拥比例  不能大于签约时的平台抽拥比例

//        sepRefundInfo.put("couponAmount", 12);
//         下单付款用户id
            // 如果未分账则只需上送 cnlRefundAmount 字段即可
            // TODO 处理退款通知问题
            map.put("sepRefundInfo", JsonUtil.toJson(sepRefundInfo));
            map.put("respUrl", notifyUrl + "/api/refund/notify/" + getInterfaceCode() + "/" + createRefundOrderVO.getRefundOrderId());
            TongLianClient.SendBuild sendBuild = new TongLianClient.SendBuild(SnowflakeIdUtil.nextId(), "2294", map);
            TongLianClient tongLianClient = new TongLianClient(tongLianIsvAndMchConfig.isvConfig());
            // TODO 待处理退款接口调用记录问题
            TongLianClient.Response response = tongLianClient.sendRequest(sendBuild, tlUrl);
            if (Boolean.TRUE.equals(response.getSuccess())) {
                // 订单状态 0进行中  1 交易成功 2 交易失败
                String result = response.get("result").asText();
                if ("1".equals(result)) {
                    handlerResult.setChannelState(ChannelState.PROCESSING.getCode());
                }
                handlerResult.setChannelOrderNo(response.get("respTraceNum").asText());
            } else {
                handlerResult.setChannelErrMsg(response.getErrorMsg());
                handlerResult.setChannelErrCode(response.getRespCode());
                handlerResult.setChannelState(ChannelState.CHANNEL_ERROR.getCode());
            }
            handlerResult.setChannelOriginResponse(String.valueOf(response.getResult()));
            handlerResult.setChannelAttach(String.valueOf(response.getResult()));
            return handlerResult;
        } catch (ChannelHandlerException e) {
            log.error("通联申请退款发生异常: ", e);
            throw ChannelHandlerException.system(e.getMessage());
        }
    }

    /**
     * 退款查单接口
     *
     * @param refundOrder
     * @param mchInfo
     */
    @Override
    public RefundChannelHandlerResult query(RefundOrder refundOrder, MchInfoVO mchInfo) throws Exception {
        return null;
    }
}
