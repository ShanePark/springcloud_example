package com.example.orderservice.messagequeue.dto;

public record Payload(
        String order_id,
        String user_id,
        String product_id,
        Integer quantity,
        Integer unit_price,
        Integer total_price
) {
}
