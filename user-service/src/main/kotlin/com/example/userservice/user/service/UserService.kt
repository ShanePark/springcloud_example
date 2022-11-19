package com.example.userservice.user.service

import com.example.userservice.order.domain.dto.ResponseOrder
import com.example.userservice.user.domain.dto.CreateUserDto
import com.example.userservice.user.domain.dto.UserDto
import com.example.userservice.user.domain.entity.User
import com.example.userservice.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserDetailsService {

    fun createUser(createUserDto: CreateUserDto): UserDto {
        val userDto = UserDto.of(createUserDto, passwordEncoder)
        userRepository.save(userDto.toEntity())
        return userDto
    }

    fun findUser(userId: String): UserDto {
        val user = userRepository.findByUserId(userId).orElseThrow()
        val orders: List<ResponseOrder> = mutableListOf()
        return user.toDto(orders)
    }

    fun findAllUsers(): Iterable<User> {
        return userRepository.findAll()
    }

    override fun loadUserByUsername(email: String): UserDetails {
        userRepository.findByEmail(email)?.let {
            return org.springframework.security.core.userdetails.User(
                it.email,
                it.encryptedPassword,
                true,
                true,
                true,
                true,
                mutableListOf()
            )
        } ?: throw UsernameNotFoundException("User not found : $email")
    }

}
