package com.nemana.payment.dao;

import lombok.Data;

import java.time.Instant;

@Data
public class Payment {
    private Order order;
    private String paymentId;
    private Instant paymentTime;
}
