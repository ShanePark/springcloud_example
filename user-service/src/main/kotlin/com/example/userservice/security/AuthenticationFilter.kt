package com.example.userservice.security

import com.example.userservice.user.domain.dto.RequestLogin
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val body = request?.inputStream?.bufferedReader()?.readText()
        println(body)

        val requestLogin = RequestLogin(body)

        val token = UsernamePasswordAuthenticationToken(requestLogin.email, requestLogin.password)
        return authenticationManager.authenticate(token)
    }

    private fun RequestLogin(body: String?): RequestLogin {
        System.err.println("body = $body")
        TODO("Not yet implemented")
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        super.successfulAuthentication(request, response, chain, authResult)
    }
}
