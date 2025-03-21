package com.baosight.payment.utils;

import com.baosight.distributedid.toolkit.SnowflakeIdUtil;

/**
 * @program: payment-center
 * @description: id生成工具类
 * @author: L.J.Ran
 * @create: 2025/3/18
 */
public class IdGenUtil {

    /**
     * 将指定id进行切割并创建一个信息的
     *
     * @param id 需要进行切割的id
     */
    public static String generateId(Long id) {
        long l = id % 1000000;
        return SnowflakeIdUtil.nextIdStr() + l;
    }
    public static String generateId(Long id, Long newId){
        long l = id % 1000000;
        return newId + "" + l;
    }
}
