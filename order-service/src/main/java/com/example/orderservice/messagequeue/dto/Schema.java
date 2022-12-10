package com.example.orderservice.messagequeue.dto;

import java.util.List;

public record Schema(
        String type,
        List<Field> fields,
        boolean optional,
        String name
) {
}
