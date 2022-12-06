package com.example.orderservice.controller;

import com.example.orderservice.domain.dto.CreateOrderDto;
import com.example.orderservice.domain.dto.OrderDto;
import com.example.orderservice.domain.dto.ResponseOrder;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final Environment env;
    private final KafkaProducer kafkaProducer;

    @GetMapping("/health_check")
    public String status() {
        var port = env.getProperty("local.server.port");
        return "It's Working in Order Service on PORT " + port;
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(
            @RequestBody CreateOrderDto createOrderDto,
            @PathVariable String userId
    ) {
        var order = orderService.createOrder(createOrderDto, userId);
        ResponseOrder responseOrder = ResponseOrder.of(order);

        // send order info to kafka
        kafkaProducer.send("example-catalog-topic", OrderDto.of(userId, responseOrder));

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("{userId}/orders")
    public List<ResponseOrder> getOrders(@PathVariable String userId) {
        return orderService.getOrdersByUserId(userId).stream()
                .map(ResponseOrder::of)
                .toList();
    }

    @GetMapping("/orders/{orderId}")
    public ResponseOrder getOrderByOrderId(@PathVariable String orderId) {
        var order = orderService.getOrderByOrderId(orderId);
        return ResponseOrder.of(order);
    }

}
