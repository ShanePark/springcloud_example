package com.example.orderservice.domain.entity;

import com.example.orderservice.domain.dto.CreateOrderDto;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer unitPrice;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, unique = true)
    private String orderId;

    @Column(updatable = false, insertable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public static Order from(CreateOrderDto orderDetails, String userId) {
        var order = new Order();
        order.productId = orderDetails.productId();
        order.quantity = orderDetails.quantity();
        order.unitPrice = orderDetails.unitPrice();
        order.totalPrice = orderDetails.quantity() * orderDetails.unitPrice();
        order.userId = userId;
        order.orderId = UUID.randomUUID().toString();
        return order;
    }
}
