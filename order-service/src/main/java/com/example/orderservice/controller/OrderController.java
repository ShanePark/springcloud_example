package com.example.orderservice.controller;

import com.example.orderservice.domain.dto.CreateOrderDto;
import com.example.orderservice.domain.dto.ResponseOrder;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        var port = env.getProperty("local.server.port");
        return "It's Working in Order Service on PORT " + port;
    }

    @PostMapping("/{userId}/orders")
    public ResponseOrder createOrder(
            @RequestBody CreateOrderDto createOrderDto,
            @PathVariable String userId
    ) {
        var order = orderService.createOrder(createOrderDto, userId);
        return ResponseOrder.of(order);
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
