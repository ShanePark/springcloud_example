package com.example.catalogservice.domain.entity

import org.hibernate.annotations.ColumnDefault
import java.io.Serializable
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class Catalog(
    @Column(nullable = false, unique = true, length = 120)
    var produceId: String,

    @Column(nullable = false)
    var productName: String,

    @Column(nullable = false)
    var stock: Int,

    @Column(nullable = false)
    var unitPrice: Int,

    ) : Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var id: Long? = null

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    var createdAt: Date? = null

}
