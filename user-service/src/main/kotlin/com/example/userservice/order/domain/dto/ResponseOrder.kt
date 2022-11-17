package com.example.userservice.order.domain.dto

import java.time.LocalDateTime

data class ResponseOrder(
    private val orderId: String,
    private val productId: String,
    private val quantity: Int,
    private val unitPrice: Int,
    private val totalPrice: Int,
    private val createdDate: LocalDateTime
)
