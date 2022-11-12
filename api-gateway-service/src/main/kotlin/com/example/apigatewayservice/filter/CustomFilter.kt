package com.example.apigatewayservice.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class CustomFilter : AbstractGatewayFilterFactory<CustomFilter.Config>(Config::class.java) {

    val log: Logger = getLogger(CustomFilter::class.java)

    class Config {
        // Put the configuration properties for your filter here
    }

    override fun apply(config: Config): GatewayFilter {
        // custom pre filter
        return GatewayFilter { exchange, chain ->
            val request = exchange.request
            val response = exchange.response

            log.info("Custom Pre Filter : request id -> $request.id")

            // Custom Post Filter
            chain.filter(exchange).then(Mono.fromRunnable {
                log.info("Custom Post Filter : response code -> ${response.statusCode}")
            })
        }
    }

}
