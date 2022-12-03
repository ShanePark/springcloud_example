package com.example.userservice.client

import com.example.userservice.order.domain.dto.ResponseOrder
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient("order-service")
interface OrderServiceClient {

    @GetMapping("/order-service/{userId}/orders_wrong")
    fun getOrders(@PathVariable userId: String): List<ResponseOrder>

}
