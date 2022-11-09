package com.example.myfirstservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyFirstServiceApplication

fun main(args: Array<String>) {
    runApplication<MyFirstServiceApplication>(*args)
}
