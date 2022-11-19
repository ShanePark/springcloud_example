package com.example.userservice.user.domain.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class RequestLogin(

    @field:NotNull(message = "email is required")
    @field:Size(min = 2, message = "email must be greater than 2 characters")
    val email: String,

    @field:NotNull(message = "password is required")
    @field:Size(min = 8, message = "password must be greater than 8 characters")
    val password: String,

    )
