package com.example.orderservice.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDto implements Serializable {
    private String productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
    private String orderId;
    private String userId;

    public static OrderDto of(String userId, ResponseOrder responseOrder) {
        var orderDto = new OrderDto();
        orderDto.setProductId(responseOrder.productId());
        orderDto.setQuantity(responseOrder.quantity());
        orderDto.setUnitPrice(responseOrder.unitPrice());
        orderDto.setTotalPrice(responseOrder.totalPrice());
        orderDto.setOrderId(responseOrder.orderId());
        orderDto.setUserId(userId);
        return orderDto;
    }
}
