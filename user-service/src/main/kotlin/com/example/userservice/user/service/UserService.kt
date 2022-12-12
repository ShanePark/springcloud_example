package com.example.userservice.user.service

import com.example.userservice.client.OrderServiceClient
import com.example.userservice.order.domain.dto.ResponseOrder
import com.example.userservice.user.domain.dto.CreateUserDto
import com.example.userservice.user.domain.dto.UserDto
import com.example.userservice.user.domain.entity.User
import com.example.userservice.user.repository.UserRepository
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory
import org.springframework.core.env.Environment
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val restTemplate: RestTemplate,
    private val env: Environment,
    private val orderServiceClient: OrderServiceClient,
    private val circuitBreakerFactory: CircuitBreakerFactory<*, *>,
) : UserDetailsService {

    val log: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(UserService::class.java)

    fun createUser(createUserDto: CreateUserDto): UserDto {
        val userDto = UserDto.of(createUserDto, passwordEncoder)
        userRepository.save(userDto.toEntity())
        return userDto
    }

    fun findUser(userId: String): UserDto {
        val user = userRepository.findByUserId(userId).orElseThrow()

        val circuitBreaker = circuitBreakerFactory.create("circuitBreaker")
        val orders: List<ResponseOrder> = circuitBreaker.run({
            orderServiceClient.getOrders(userId)
        }, {
            log.error("Circuit breaker has been opened", it)
            emptyList()
        })

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

    fun getUserDetailsByEmail(email: String): UserDto {
        val user = userRepository.findByEmail(email)
        return user?.let {
            return it.toDto(emptyList())
        } ?: throw UsernameNotFoundException("User not found : $email")
    }
}
