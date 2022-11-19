package com.example.orderservice.service;

import com.example.orderservice.domain.dto.CreateOrderDto;
import com.example.orderservice.domain.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(CreateOrderDto createOrderDto, String userId) {
        var order = Order.from(createOrderDto, userId);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderByOrderId(String orderId) {
        return orderRepository.findByOrderId(orderId).orElseThrow();
    }


}
