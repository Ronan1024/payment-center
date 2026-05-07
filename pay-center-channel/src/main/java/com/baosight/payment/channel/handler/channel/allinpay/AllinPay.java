package com.baosight.payment.channel.handler.channel.allinpay;

import com.baosight.payment.channel.handler.channel.IChannel;
import com.baosight.payment.enums.PayingAgency;
import org.springframework.stereotype.Component;

import static com.baosight.payment.channel.enums.ChannelCode.ALLIN_PAY;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/3/4
 */
@Component
public class AllinPay implements IChannel {
    /**
     * 渠道编号
     */
    @Override
    public String channelCode() {
        return ALLIN_PAY.code();
    }

    /**
     * 渠道名称
     *
     */
    @Override
    public String channelName() {
        return ALLIN_PAY.desc();
    }

    /**
     * 支付机构
     */
    @Override
    public PayingAgency payingAgency() {
        return PayingAgency.ALL_IN;
    }

    /**
     * 支付渠道发起支付处理
     */
    @Override
    public String pay() {
        // 获取后台通知地址
        return "";
    }


    private enum AllinPayOrderResultEnum {
        /**
         * 进行中
         */


        /**
         * 交易成功
         */
        SUCCESS("1"),


        /**
         * 交易失败
         */
        FAIL("2"),

        ;

        private final String code;

        AllinPayOrderResultEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
