package com.example.userservice.user.domain.dto

import com.example.userservice.order.domain.dto.ResponseOrder
import com.example.userservice.user.domain.entity.User
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

data class UserDto(
    val email: String,
    val name: String,
    val password: String,
    val userId: String,
    val encryptedPassword: String,
    val orders: List<ResponseOrder>,
) {
    fun toEntity() = User(
        email = this.email,
        name = this.name,
        userId = this.userId,
        encryptedPassword = this.encryptedPassword,
    )

    companion object {
        fun of(createUserDto: CreateUserDto, passwordEncoder: PasswordEncoder): UserDto {
            return UserDto(
                email = createUserDto.email,
                name = createUserDto.name,
                password = createUserDto.password,
                userId = UUID.randomUUID().toString(),
                encryptedPassword = passwordEncoder.encode(createUserDto.password),
                orders = listOf(),
            )
        }
    }
}
