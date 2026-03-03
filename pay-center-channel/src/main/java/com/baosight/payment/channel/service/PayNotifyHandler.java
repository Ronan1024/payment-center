package com.baosight.payment.channel.service;

import jakarta.servlet.http.HttpServletRequest;

public interface PayNotifyHandler {


    boolean support(String body, HttpServletRequest request);


    String handle(String body, HttpServletRequest request);

}
