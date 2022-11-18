package com.example.catalogservice.listener

import com.example.catalogservice.domain.entity.Catalog
import com.example.catalogservice.repository.CatalogRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class CatalogInitializer(
    private val CatalogRepository: CatalogRepository,
) {

    @PostConstruct
    fun init() {
        CatalogRepository.save(Catalog("CATALOG-001", "Berlin", 100, 1500))
        CatalogRepository.save(Catalog("CATALOG-002", "Seoul", 25, 700000))
        CatalogRepository.save(Catalog("CATALOG-003", "NewYork", 15, 15))
    }
}
