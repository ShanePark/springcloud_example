package com.example.orderservice.messagequeue.dto;

public record KafkaOrderDto(
        Schema schema,
        Payload payload
) {
}
