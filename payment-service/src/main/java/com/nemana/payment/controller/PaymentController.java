package com.nemana.payment.controller;

import com.nemana.payment.dao.Order;
import com.nemana.payment.dao.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @PostMapping("/pay")
    public Payment doPayment(@RequestBody Order order) {
        log.info("call rabbit mq to do payment");
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setPaymentTime(Instant.now());

        return payment;
    }
}
