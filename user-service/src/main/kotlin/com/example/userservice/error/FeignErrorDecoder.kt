package com.example.userservice.error

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class FeignErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception? {
        when (response.status()) {
            400 -> {
                return null
            }

            404 -> {
                if (methodKey.contains("getOrders")) {
                    return ResponseStatusException(HttpStatus.valueOf(404), "User not found")
                }
            }

            else -> {
                return Exception(response.reason())
            }
        }
        return null
    }

}
