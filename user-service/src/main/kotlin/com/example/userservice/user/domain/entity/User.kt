package com.example.userservice.user.domain.entity

import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, length = 50, unique = true)
    var email: String,

    @Column(nullable = false, length = 50)
    var name: String,

    @Column(nullable = false, unique = true)
    var userId: String,

    @Column(nullable = false)
    var encryptedPassword: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}
