package com.nemana.order.controller;


import com.nemana.order.dao.OrderEntity;
import com.nemana.order.exceptions.OrderNotFoundException;
import com.nemana.order.model.Payment;
import com.nemana.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("get/{orderId}")
    public OrderEntity getOrderDetails(@PathVariable Integer orderId) throws Throwable {
        log.info("Inside getOrderDetails");
        Optional<OrderEntity> orderEntity =  orderService.getOrderById(orderId);
        return orderEntity.orElseThrow(()-> new OrderNotFoundException("Order could not be found in the database with order id:"+orderId));
    }

    @GetMapping("/all")
    public List<OrderEntity> getAllOrders() {
        log.info("Inside get All orders");
        return orderService.getAllOrders();
    }

    @PostMapping("/createRest")
    public OrderEntity createOrderRestTemplate(@RequestBody OrderEntity order) throws URISyntaxException {
        log.info("Inside createOrder Rest Template");
        OrderEntity response = orderService.createOrderRestTemplate(order);
        log.info("Invoking payment service for payment");
        Payment payment = orderService.doPaymentRestTemplate(response);
        if(payment != null) {
            response.setComments("your payment is successful with payment id:"+payment.getPaymentId()+" done @:"+ payment.getPaymentTime());
        }
        return response;
    }

    @PostMapping("/create")
    public Mono<OrderEntity> createOrder(@RequestBody OrderEntity order) {
        log.info("Inside createOrder");
        OrderEntity response = orderService.createOrderRestTemplate(order);
        log.info("Invoking payment service for payment");
        Mono<Payment> payment = orderService.doPayment(response);
        if(payment != null) {
            return  payment.flatMap(paym-> setPaymentDetails(paym,response));
        }
        return Mono.empty();
    }

    private Mono<OrderEntity> invokePaymentService(OrderEntity order) {
        Mono<OrderEntity> orderMono = Mono.empty();
        Mono<Payment> payment = orderService.doPayment(order);
        if(payment != null) {
            orderMono = payment.flatMap(paym-> setPaymentDetails(paym,order));
        }
        return orderMono;
    }

    private Mono<OrderEntity> setPaymentDetails(Payment payment, OrderEntity order ) {
        order.setComments("your payment is successful with payment id:"+payment.getPaymentId()+"done @:"+ payment.getPaymentTime());
        return Mono.just(order);
    }

    @DeleteMapping("delete/{id}")
    public void removeOrderById(@PathVariable Integer id) {
        log.info("inside delete order");
        orderService.removeOrderById(id);
    }
}
