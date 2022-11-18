package com.example.catalogservice.service

import com.example.catalogservice.domain.entity.Catalog
import com.example.catalogservice.repository.CatalogRepository
import org.springframework.stereotype.Service

@Service
class CatalogService(
    private val catalogRepository: CatalogRepository
) {
    fun findAllCatalogs(): Iterable<Catalog> {
        return catalogRepository.findAll()
    }
}
