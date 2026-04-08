package com.baosight.payment.utils;

import com.baosight.payment.enums.PayClientType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 系统编号工具
 *
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2026/4/8
 */
public class CodeUtil {


    /**
     * 服务商编号生成 固定头信息 SP + 标识位(1 用户注册 4 运营人员添加) + 时间 +5位序列位
     *
     * @param genClient 创建人员客户端
     * @param sequence  序列号
     */
    public static String isvGenCode(PayClientType genClient, String sequence) {
        String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return "SP" + genClient.code() + format + sequence;
    }

    /**
     * 普通商户编号生成 固定头信息 M + 标识位(2 用户注册 4 运营人员添加) + 时间 +4位序列位(当日自增序列)
     */
    public static String merchantCode(PayClientType genClient, String sequence) {
        String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return "M" + genClient.code() + format + sequence;
    }

    /**
     * 特约商户编号生成 固定头信息 SM 标识位(3 用户注册 4 运营人员添加 1 服务商人员添加) + 时间 + 服务商序列位 + 4位序列位(当日自增序列)
     */
    public static String subMerchantCode(PayClientType genClient, String sequence, String isvCode) {
        if (isvCode.startsWith("SP")) {
            isvCode = getIsvSequence(isvCode);
        }
        String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return "SM" + genClient.code() + isvCode + format + sequence;
    }

    /**
     * 拆解服务商编号获取序列号
     */
    public static String getIsvSequence(String code) {
        return code.substring(code.length() - 5);
    }

}
