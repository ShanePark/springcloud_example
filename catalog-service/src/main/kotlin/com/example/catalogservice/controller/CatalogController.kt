package com.example.catalogservice.controller

import com.example.catalogservice.domain.dto.ResponseCatalog
import com.example.catalogservice.service.CatalogService
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/catalog-service")
class CatalogController(
    private val env: Environment,
    private val catalogService: CatalogService,
) {

    @GetMapping("/health_check")
    fun status(): String {
        val port = env.getProperty("local.server.port")
        return "It's Working in Catalog Service on PORT $port"
    }

    @GetMapping("/catalogs")
    fun findAllCatalogs(): List<ResponseCatalog> {
        return catalogService.findAllCatalogs().map { ResponseCatalog.of(it) }.toList()
    }

}
