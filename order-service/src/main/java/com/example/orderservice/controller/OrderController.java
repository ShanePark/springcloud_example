package com.example.orderservice.controller;

import com.example.orderservice.domain.dto.CreateOrderDto;
import com.example.orderservice.domain.dto.OrderDto;
import com.example.orderservice.domain.dto.ResponseOrder;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final Environment env;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

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

        int quantity = createOrderDto.quantity();
        int unitPrice = createOrderDto.unitPrice();

        OrderDto orderDto = OrderDto.builder()
                .productId(createOrderDto.productId())
                .quantity(quantity)
                .unitPrice(unitPrice)
                .totalPrice(unitPrice * quantity)
                .orderId(UUID.randomUUID().toString())
                .userId(userId)
                .build();

        // send order info to kafka
        kafkaProducer.send("example-catalog-topic", orderDto);

        // Kafka Connect will be used to save the order to the database instead of JPA
        orderProducer.send("orders", orderDto);

        ResponseOrder responseOrder = ResponseOrder.of(orderDto);
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
