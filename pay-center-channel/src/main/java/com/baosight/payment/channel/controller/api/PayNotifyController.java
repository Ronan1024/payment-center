package com.baosight.payment.channel.controller.api;


import com.baosight.payment.channel.handler.NotifyDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/call/back")
@RequiredArgsConstructor
public class PayNotifyController {

    @Autowired
    private NotifyDispatcher dispatcher;

    @PostMapping("/notify")
    public ResponseEntity<String> notify(@RequestBody(required = false) String body, HttpServletRequest request) {
        if (body == null) {
            body = new String(request.getParameterMap().toString());
        }
        String response = dispatcher.dispatch(body, request);
        return ResponseEntity.ok(response);
    }
}
