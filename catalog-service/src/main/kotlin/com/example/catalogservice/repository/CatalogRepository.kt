package com.example.catalogservice.repository

import com.example.catalogservice.domain.entity.Catalog
import org.springframework.data.repository.CrudRepository

interface CatalogRepository : CrudRepository<Catalog, Long> {

    fun findByProduceId(produceId: String): Catalog?
}
