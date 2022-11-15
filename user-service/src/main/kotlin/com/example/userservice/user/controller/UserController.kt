package com.example.userservice.user.controller

import com.example.userservice.Greeting
import com.example.userservice.user.domain.dto.CreateUserDto
import com.example.userservice.user.domain.dto.UserDto
import com.example.userservice.user.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
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
    ): String {
        val userDto = UserDto.of(createUserDto)
        userService.createUser(userDto)
        return "Create user methoid is called"
    }

}
