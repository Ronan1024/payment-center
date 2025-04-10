package com.baosight.payment.system.handler.option;

import java.util.List;

/**
 * @program: payment-center
 * @description:
 * @author: L.J.Ran
 * @create: 2025/3/25
 */
public interface IPayAgencyOption<T> {

    String mark();

    List<String> option(T t);
}
