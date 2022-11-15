package com.example.userservice.user.repository

import com.example.userservice.user.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>

