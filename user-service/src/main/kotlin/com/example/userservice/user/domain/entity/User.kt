package com.example.userservice.user.domain.entity

import com.example.userservice.order.domain.dto.ResponseOrder
import com.example.userservice.user.domain.dto.UserDto
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
    fun toDto(orders: List<ResponseOrder>): UserDto {
        return UserDto(
            email = this.email,
            name = this.name,
            password = this.encryptedPassword,
            userId = this.userId,
            encryptedPassword = this.encryptedPassword,
            orders = orders
        )
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}
