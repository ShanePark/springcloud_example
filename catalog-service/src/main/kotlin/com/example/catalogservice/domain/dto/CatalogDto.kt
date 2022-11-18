package com.example.catalogservice.domain.dto

data class CatalogDto(
    val productId: String,
    val quantity: Int,
    val unitPrice: Int,
    val totalPrice: Int,

    val orderId: String,
    val userId: String,
)
