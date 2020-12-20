package com.nemana.order.model;

import com.nemana.order.dao.OrderEntity;
import lombok.Data;

import java.time.Instant;

@Data
public class Payment {
    private OrderEntity order;
    private String paymentId;
    private Instant paymentTime;
}
