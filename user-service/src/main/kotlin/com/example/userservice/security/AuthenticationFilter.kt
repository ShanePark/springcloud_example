package com.example.userservice.security

import com.example.userservice.user.domain.dto.RequestLogin
import com.example.userservice.user.domain.dto.UserDto
import com.example.userservice.user.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    private val objectMapper: ObjectMapper,
    private val authManager: AuthenticationManager,
    private val userService: UserService,
    private val env: Environment,
) : UsernamePasswordAuthenticationFilter() {

    private val log = org.slf4j.LoggerFactory.getLogger(this::class.java)

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {

        val requestLogin = objectMapper.readValue(request.inputStream, RequestLogin::class.java)
        val token = UsernamePasswordAuthenticationToken(requestLogin.email, requestLogin.password)
        return authManager.authenticate(token)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication
    ) {
        val user: User = authResult.principal as User
        val userDetail: UserDto = userService.getUserDetailsByEmail(user.username)

        val expiration = Date(System.currentTimeMillis() + env.getProperty("token.expiration_time")!!.toLong())

        val token = Jwts.builder()
            .setSubject(userDetail.userId)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
            .compact()

        log.info("User $userDetail authenticated successfully. Token: $token")
        response.addHeader("token", token)
        response.addHeader("userId", userDetail.userId)
    }
}
