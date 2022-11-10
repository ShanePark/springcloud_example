package com.example.myfirstservice

import org.slf4j.LoggerFactory.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/first-service")
class FirstServiceController {

    val log = getLogger(FirstServiceController::class.java)

    @GetMapping("/welcome")
    fun welcome() = "Welcome to my first service!"

    @GetMapping("/message")
    fun message(@RequestHeader("first-request") header: String): String {
        log.info("header: $header")
        return "Hello World in First Service"
    }

}
