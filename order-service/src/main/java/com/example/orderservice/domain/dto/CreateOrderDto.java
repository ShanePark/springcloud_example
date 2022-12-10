package com.example.orderservice.domain.dto;

public record CreateOrderDto(
        String productId,
        int quantity,
        int unitPrice
) {
}
