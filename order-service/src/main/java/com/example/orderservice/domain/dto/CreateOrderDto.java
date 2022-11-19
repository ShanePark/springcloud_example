package com.example.orderservice.domain.dto;

public record CreateOrderDto(
        String productId,
        Integer quantity,
        Integer unitPrice
) {
}
