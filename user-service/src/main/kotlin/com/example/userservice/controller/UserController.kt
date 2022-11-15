package com.example.userservice.controller

import com.example.userservice.vo.Greeting
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class UserController(
    private val greeting: Greeting
) {

    @GetMapping("/health_check")
    fun status(): String {
        return "It's Working in User Service"
    }

    @GetMapping("/welcome")
    fun welcome(): String {
        return greeting.message
    }

}
