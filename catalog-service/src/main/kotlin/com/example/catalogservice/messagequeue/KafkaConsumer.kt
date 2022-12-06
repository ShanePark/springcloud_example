package com.example.catalogservice.messagequeue

import com.example.catalogservice.repository.CatalogRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer(
    private val repository: CatalogRepository,
    private val mapper: ObjectMapper
) {

    private val log = org.slf4j.LoggerFactory.getLogger(KafkaConsumer::class.java)

    @KafkaListener(topics = ["example-catalog-topic"])
    fun updateQuantity(kafkaMessage: String) {
        log.info("Kafka Message: $kafkaMessage")

        val map = mapper.readValue(kafkaMessage, HashMap<Any, Any>().javaClass)
        val productId = map["productId"].toString()
        val findByProduceId = repository.findByProduceId(productId)
        findByProduceId?.let {
            it.stock -= map["quantity"].toString().toInt()
            repository.save(it)
        }
    }

}
