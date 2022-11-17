package com.example.userservice.user.repository

import com.example.userservice.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByUserId(userId: String): Optional<User>
}

