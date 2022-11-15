package com.example.userservice.vo

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Greeting {

    @Value("\${greeting.message}")
    lateinit var message: String

}
