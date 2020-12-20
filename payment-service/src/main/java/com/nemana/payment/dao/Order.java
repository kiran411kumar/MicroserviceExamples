package com.nemana.payment.dao;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {
    private Integer orderId;
    private BigDecimal totalPayment;
}
