package com.example.userservice.user.controller

import com.example.userservice.Greeting
import com.example.userservice.user.domain.dto.CreateUserDto
import com.example.userservice.user.domain.dto.ResponseUserDto
import com.example.userservice.user.domain.dto.UserDto
import com.example.userservice.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user-service")
class UserController(
    private val greeting: Greeting,
    private val userService: UserService
) {

    @GetMapping("/health_check")
    fun status(): String {
        return "It's Working in User Service"
    }

    @GetMapping("/welcome")
    fun welcome(): String {
        return greeting.message
    }

    @PostMapping("/users")
    fun createUser(
        @RequestBody createUserDto: CreateUserDto
    ): ResponseEntity<ResponseUserDto> {
        val userDto = UserDto.of(createUserDto)
        userService.createUser(userDto)
        val responseUser = ResponseUserDto.of(userDto)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(responseUser)
    }

}
