package com.example.catalogservice.domain.dto

import com.example.catalogservice.domain.entity.Catalog
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseCatalog(
    val productId: String,
    val productName: String,
    val stock: Int,
    val unitPrice: Int,
    val createdAt: Date,
) {
    companion object {
        fun of(catalog: Catalog): ResponseCatalog {
            return ResponseCatalog(
                productId = catalog.produceId,
                productName = catalog.productName,
                stock = catalog.stock,
                unitPrice = catalog.unitPrice,
                createdAt = catalog.createdAt!!,
            )
        }
    }
}
