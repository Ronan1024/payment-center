package com.baosight.payment.notify.handler;

import cn.hutool.core.net.url.UrlBuilder;
import com.baosight.payment.enums.NotifyState;
import com.baosight.payment.notify.utils.OkHttp;
import com.baosight.payment.settlement.api.MchAccountApi;
import com.baosight.payment.settlement.vo.MchAccountRecordVO;
import com.baosight.utils.json.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/4/22
 */
@Slf4j
@RequiredArgsConstructor
@Component(value = WithdrawNotifyHandler.MARK)
public class WithdrawNotifyHandler implements INotifyHandler {
    public static final String MARK = "withdrawNotifyHandler";
    private final MchAccountApi mchAccountApi;


    /**
     * 发起通知
     *
     * @param orderId   订单id
     * @param notifyUrl 通知url
     */
    @Override
    public String notify(Long orderId, String notifyUrl) {
        String result;
        try {
            String host = notifyUrl.split("\\?")[0];
            MchAccountRecordVO mchAccountRecord = mchAccountApi.getMchAccountInfo(orderId);
            Map<String, String> map = new HashMap<>();
            map.put("orderId", String.valueOf(mchAccountRecord.getId()));
            map.put("mchOrderId", mchAccountRecord.getMchOrderId());
            map.put("state", "1");
            result = OkHttp.postJson(host, JsonUtil.toJson(map));
        } catch (Exception e) {
            log.error("http error", e);
            result = "连接[" + UrlBuilder.of(notifyUrl).getHost() + "]异常:【" + e.getMessage() + "】";
        }
        return result;
    }


    /**
     * 更新通知发送状态
     *
     * @param orderId     订单id
     * @param notifyState 通知状态
     */
    @Override
    public Boolean updateNotifySent(Long orderId, NotifyState notifyState, String notifyUrl) {
        return Boolean.TRUE;
    }
}
