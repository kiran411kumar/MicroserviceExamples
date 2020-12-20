package com.nemana.order.service;



import com.nemana.order.dao.OrderEntity;
import com.nemana.order.model.Payment;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderEntity> getAllOrders();
    Optional<OrderEntity> getOrderById(Integer id);
    Mono<OrderEntity> createOrder(Mono<OrderEntity> order);
    void removeOrderById(Integer id);
    Mono<Payment> doPayment(OrderEntity orde);

    OrderEntity createOrderRestTemplate(OrderEntity order);

    Payment doPaymentRestTemplate(OrderEntity order) throws URISyntaxException;
}
