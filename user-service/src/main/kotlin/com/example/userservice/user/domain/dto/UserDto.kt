package com.example.userservice.user.domain.dto

import com.example.userservice.user.domain.entity.User
import java.time.LocalDateTime
import java.util.*

data class UserDto(
    val email: String,
    val name: String,
    val password: String,
    val userId: String,
    val createdAt: LocalDateTime,
    val encryptedPassword: String
) {
    fun toEntity() = User(
        email = email,
        name = name,
        userId = userId,
        encryptedPassword = encryptedPassword
    )

    companion object {
        fun of(createUserDto: CreateUserDto): UserDto {
            return UserDto(
                email = createUserDto.email,
                name = createUserDto.name,
                password = createUserDto.password,
                userId = UUID.randomUUID().toString(),
                createdAt = LocalDateTime.now(),
                encryptedPassword = createUserDto.password // TODO password encryption
            )
        }
    }
}
