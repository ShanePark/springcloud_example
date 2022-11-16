package com.example.userservice.user.domain.dto

data class ResponseUserDto(
    val email: String,
    val name: String,
    val userId: String
) {
    companion object {
        fun of(userDto: UserDto): ResponseUserDto {
            return ResponseUserDto(
                email = userDto.email,
                name = userDto.name,
                userId = userDto.userId
            )
        }
    }
}
