package com.example.myfirstservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class FirstServiceController {

    @GetMapping("/welcome")
    fun welcome() = "Welcome to my first second service!"

}