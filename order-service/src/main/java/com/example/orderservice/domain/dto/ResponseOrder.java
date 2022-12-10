package com.example.orderservice.domain.dto;

import com.example.orderservice.domain.entity.Order;

import java.time.LocalDateTime;

public record ResponseOrder(
        String orderId,
        String productId,
        Integer quantity,
        Integer unitPrice,
        Integer totalPrice,
        LocalDateTime createdDate
) {
    public static ResponseOrder of(Order order) {
        return new ResponseOrder(
                order.getOrderId(),
                order.getProductId(),
                order.getQuantity(),
                order.getUnitPrice(),
                order.getTotalPrice(),
                order.getCreatedAt()
        );
    }

    public static ResponseOrder of(OrderDto orderDto) {
        return new ResponseOrder(
                orderDto.getOrderId(),
                orderDto.getProductId(),
                orderDto.getQuantity(),
                orderDto.getUnitPrice(),
                orderDto.getTotalPrice(),
                LocalDateTime.now()
        );
    }
}
