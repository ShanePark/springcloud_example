package com.example.apigatewayservice

import org.springframework.boot.actuate.trace.http.HttpTraceRepository
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ApiGatewayServiceApplication

fun main(args: Array<String>) {
    runApplication<ApiGatewayServiceApplication>(*args)
}

@Bean
fun httpTraceRepository(): HttpTraceRepository {
    return InMemoryHttpTraceRepository()
}
