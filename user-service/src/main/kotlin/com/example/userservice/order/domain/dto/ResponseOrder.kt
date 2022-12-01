package com.example.userservice.order.domain.dto

import java.time.LocalDateTime

data class ResponseOrder(
    val orderId: String,
    val productId: String,
    val quantity: Int,
    val unitPrice: Int,
    val totalPrice: Int,
    val createdDate: LocalDateTime
)
