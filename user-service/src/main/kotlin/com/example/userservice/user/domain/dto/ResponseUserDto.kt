package com.example.userservice.user.domain.dto

import com.example.userservice.order.domain.dto.ResponseOrder
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseUserDto(
    val email: String,
    val name: String,
    val userId: String,
    val orders: List<ResponseOrder>? = null
) {
    companion object {
        fun of(userDto: UserDto): ResponseUserDto {
            return ResponseUserDto(
                email = userDto.email,
                name = userDto.name,
                userId = userDto.userId,
                orders = userDto.orders
            )
        }
    }
}
