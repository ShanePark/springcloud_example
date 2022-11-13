package com.example.apigatewayservice.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class LoggingFilter : AbstractGatewayFilterFactory<LoggingFilter.Config>(Config::class.java) {

    val log: Logger = getLogger(LoggingFilter::class.java)

    class Config(
        val baseMessage: String,
        val preLogger: Boolean,
        val postLogger: Boolean
    )

    override fun apply(config: Config): GatewayFilter {
        val filter = OrderedGatewayFilter({ exchange, chain ->
            log.info("Logging Filter baseMessage: {}", config.baseMessage)

            if (config.preLogger) {
                log.info("Logging PRE filter: request id -> ${exchange.request.id}")
            }
            chain.filter(exchange).then(Mono.fromRunnable {
                if (config.postLogger) {
                    log.info("Logging POST filter: response code -> ${exchange.response.statusCode}")
                }
            })
        }, Ordered.HIGHEST_PRECEDENCE)
        return filter
    }

}
