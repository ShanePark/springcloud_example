package com.example.userservice.user.service

import com.example.userservice.user.domain.dto.UserDto
import com.example.userservice.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun createUser(userDto: UserDto): UserDto {
        userRepository.save(userDto.toEntity())
        return userDto
    }
}
