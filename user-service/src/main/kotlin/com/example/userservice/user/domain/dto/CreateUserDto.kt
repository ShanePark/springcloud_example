package com.example.userservice.user.domain.dto

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class CreateUserDto(
    @field:NotNull(message = "Email cannot be null")
    @Email
    @Size(min = 2, message = "Email should have at least 2 characters")
    val email: String,

    @field:NotNull(message = "name cannot be null")
    @Size(min = 2, message = "name should have at least 2 characters")
    val name: String,

    @field:NotNull(message = "password cannot be null")
    @Size(min = 8, max = 16, message = "password must be equal or grater than 8 characters and less than 16 characters")
    val password: String
)
