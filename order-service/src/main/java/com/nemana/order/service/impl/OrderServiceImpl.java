package com.nemana.order.service.impl;


import com.nemana.order.dao.OrderEntity;
import com.nemana.order.dao.OrderServiceDao;
import com.nemana.order.model.Payment;
import com.nemana.order.service.OrderService;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderServiceDao orderServiceDao;

    @Autowired
    WebClient webClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EurekaClient eurekaClient;

    @Override
    public List<OrderEntity> getAllOrders() {
        log.info("Inside get All orders");
        return orderServiceDao.findAll();
    }

    @Override
    public Optional<OrderEntity> getOrderById(Integer id) {
        return orderServiceDao.findById(id);
    }

    @Override
    public Mono<OrderEntity> createOrder(Mono<OrderEntity> order) {
        log.info("inside Create Order with order details:"+order);
        return order.flatMap(od -> setTotalPrice(od));
    }

    private Mono<OrderEntity> setTotalPrice(OrderEntity order) {
        order.setTotalPrice(order.getItemPrice().multiply(BigDecimal.valueOf(order.getQty())));
        return Mono.just(orderServiceDao.save(order));
    }


    @Override
    public void removeOrderById(Integer id) {
        log.info("Inside remove Order by Id:"+id);
        orderServiceDao.deleteById(id);
    }

    @Override
    public Mono<Payment> doPayment(OrderEntity order) {
        log.info("webclient :" + webClient);
        Application application = eurekaClient.getApplication("PAYMENT-SERVICE");
        InstanceInfo instanceInfo = application.getInstances().get(0);
        String uri = "http://"+instanceInfo.getHostName()+":"+instanceInfo.getPort()+"/payment/pay";
        System.out.println("uri:::" + uri);
        return webClient.post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(order)
                .retrieve()
                .bodyToMono(Payment.class)
                .doOnError(e -> log.error("Got Error while doing payment", e));



    }

    @Override
    public OrderEntity createOrderRestTemplate(OrderEntity order) {
        log.info("inside createOrderRestTemplate");
        order.setTotalPrice(order.getItemPrice().multiply(BigDecimal.valueOf(order.getQty())));
        return orderServiceDao.save(order);
    }

    @Override
    public Payment doPaymentRestTemplate(OrderEntity order) throws URISyntaxException {
        Application application = eurekaClient.getApplication("PAYMENT-SERVICE");
        InstanceInfo instanceInfo = application.getInstances().get(0);

        //ResponseEntity<Payment> result = restTemplate.postForEntity(new URI("http://localhost:8082/payment/pay"),order, Payment.class);
        ResponseEntity<Payment> result = restTemplate.postForEntity(new URI("http://"+instanceInfo.getHostName()+":"+instanceInfo.getPort()+"/payment/pay"),order, Payment.class);
        return result.getBody();
    }
}
