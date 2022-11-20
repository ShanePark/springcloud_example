package com.example.apigatewayservice.filter

import io.jsonwebtoken.Jwts
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthorizationHeaderFilter(
    val env: Environment
) : AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>(Config::class.java) {

    val log: Logger = LoggerFactory.getLogger(this::class.java)

    class Config {
        // Put the configuration properties for your filter here
    }

    override fun apply(config: Config?): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val request = exchange.request

            val tokenString: String = request.headers["Authorization"]?.let {
                it[0].replace("Bearer ", "")
            } ?: return@GatewayFilter onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED)

            if (!isJwtValid(tokenString)) {
                return@GatewayFilter onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED)
            }

            chain.filter(exchange)
        }
    }

    private fun isJwtValid(tokenString: String): Boolean {
        try {
            val jwt = Jwts.parser()
                .setSigningKey(env.getProperty("token.secret"))
                .parseClaimsJws(tokenString)
                .body

            val subject: String = jwt.subject ?: return false
            log.info("subject: $subject")

        } catch (e: Exception) {
            return false
        }
        return true
    }

    private fun onError(exchange: ServerWebExchange, errorMessage: String, httpStatus: HttpStatus): Mono<Void> {
        val response = exchange.response
        response.statusCode = httpStatus
        log.info(errorMessage)
        return response.setComplete()
    }
}
