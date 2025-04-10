package com.baosight.payment.system.tonglian;

import com.baosight.payment.enums.TongLianInterfaceCode;
import com.baosight.payment.system.enums.TongLianInfoType;
import com.baosight.utils.json.JsonUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员及账户类接口
 */
public class MembershipAndAccountHandler {

    /**
     * 会员绑定收银宝商户
     *
     * @param reqTraceNum     请求流水号 要求唯一
     * @param signNum         商户会员编号
     * @param sybMerchantCode 收银宝商户号
     */
    public static TongLianClient.SendBuild memberBindSyb(Long reqTraceNum, String signNum, String sybMerchantCode) {
        String transCode = TongLianInterfaceCode.BIND_SYB.getCode();
        Map<String, String> map = new HashMap<>(4);
        map.put("reqTraceNum", String.valueOf(reqTraceNum));
        map.put("signNum", signNum);
        map.put("opType", "set");
        map.put("memberRole", "收单商户");
        map.put("sybMerchantCode", sybMerchantCode);
        return new TongLianClient.SendBuild(reqTraceNum, transCode, JsonUtil.toJson(map));
    }

    /**
     * 会员绑定手机号申请
     *
     * @param reqTraceNum    请求流水号 要求唯一
     * @param signNum        商户会员编号
     * @param mobile         手机号
     * @param hasLegalPerson
     */
    public static TongLianClient.SendBuild memberBindMobile(Long reqTraceNum, String signNum, String mobile, Boolean hasLegalPerson) {
        String transCode = TongLianInterfaceCode.BIND_PHONE_REPORT.getCode();
        Map<String, String> map = new HashMap<>(4);
        map.put("reqTraceNum", String.valueOf(reqTraceNum));
        map.put("signNum", signNum);
        map.put("phone", mobile);
        map.put("phoneType", Boolean.TRUE.equals(hasLegalPerson) ? "1" : "2");
        return new TongLianClient.SendBuild(reqTraceNum, transCode, JsonUtil.toJson(map));
    }

    /**
     * 确认绑定/解绑手机号
     *
     * @param reqTraceNum       请求流水号
     * @param signNum           商户会员编号
     * @param phone             绑定或解绑手机
     * @param applyRespTraceNum 申请响应业务关联流水号
     * @param verifyCode        短信验证码
     */
    public static TongLianClient.SendBuild confirmBindPhone(Long reqTraceNum, String signNum, String phone, String applyRespTraceNum, String verifyCode) {
        String transCode = "1032";
        Map<String, String> map = new HashMap<>(6);
        map.put("reqTraceNum", String.valueOf(reqTraceNum));
        map.put("signNum", signNum);
        map.put("phone", phone);
        map.put("applyRespTraceNum", applyRespTraceNum);
        map.put("verifyCode", verifyCode);
        return new TongLianClient.SendBuild(reqTraceNum, transCode, JsonUtil.toJson(map));
    }

    /**
     * 会员信息查询
     */
    public static TongLianClient.SendBuild memberQuery(Long reqTraceNum, String signNum, TongLianInfoType infoType) {
        String transCode = "1027";
        Map<String, String> map = new HashMap<>(3);
        map.put("reqTraceNum", String.valueOf(reqTraceNum));
        map.put("signNum", signNum);
        map.put("infoType", String.valueOf(infoType.getCode()));
        return new TongLianClient.SendBuild(reqTraceNum, transCode, JsonUtil.toJson(map));
    }

    /**
     * 线上协议签订申请
     */
    public static TongLianClient.SendBuild onlineProtocolSignApply(Long reqTraceNum, String signNum, String memberName, Long rete) {
        String transCode = TongLianInterfaceCode.ONLINE_PROTOCOL_SIGN_APPLY.getCode();
        Map<String, String> map = new HashMap<>(7);
        map.put("reqTraceNum", String.valueOf(reqTraceNum));
        map.put("signNum", signNum);
        map.put("memberName", memberName);
        map.put("agreementType", "3");
        Map<String, String> agreementMap = new HashMap<>();
        agreementMap.put("couponWay", "1");
        agreementMap.put("couponRate", new BigDecimal(rete).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP).toString());
        map.put("agreementJson", JsonUtil.toJson(agreementMap));
        // TODO 签约回调地址未处理
        map.put("notifyUrl", "https://www.baidu.com");
        map.put("jumpUrl", "https://www.baidu.com/s?wd=ok");
        return new TongLianClient.SendBuild(reqTraceNum, transCode, JsonUtil.toJson(map));
    }
}
