package com.example.orderservice.messagequeue;

import com.example.orderservice.domain.dto.OrderDto;
import com.example.orderservice.messagequeue.dto.Field;
import com.example.orderservice.messagequeue.dto.KafkaOrderDto;
import com.example.orderservice.messagequeue.dto.Payload;
import com.example.orderservice.messagequeue.dto.Schema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OrderDto send(String topic, OrderDto orderDto) {
        List<Field> fields = List.of(
                new Field("string", true, "order_id"),
                new Field("string", true, "user_id"),
                new Field("string", true, "product_id"),
                new Field("int32", true, "quantity"),
                new Field("int32", true, "unit_price"),
                new Field("int32", true, "total_price")
        );

        Schema schema = new Schema("struct", fields, false, "orders");
        Payload payload = new Payload(orderDto.getOrderId(), orderDto.getUserId(), orderDto.getProductId(),
                orderDto.getQuantity(), orderDto.getUnitPrice(), orderDto.getTotalPrice());

        KafkaOrderDto kafkaOrderDto = new KafkaOrderDto(schema, payload);
        try {
            String jsonString = objectMapper.writeValueAsString(kafkaOrderDto);
            kafkaTemplate.send(topic, jsonString);
            log.info("OrderDto sent to kafka: {}", jsonString);
        } catch (JsonProcessingException e) {
            log.warn("Error while sending orderDto to kafka: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return orderDto;
    }
}
