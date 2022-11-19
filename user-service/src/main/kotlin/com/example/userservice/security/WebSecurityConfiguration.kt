package com.example.userservice.security

import com.example.userservice.user.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    private val objectMapper: ObjectMapper,
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf().disable()
//            .authorizeRequests().antMatchers("/**")
//            .hasIpAddress("127.0.0.1")
//            .and()
            .addFilter(AuthenticationFilter(objectMapper, authenticationManagerBuilder.orBuild, userService))
            .build()
    }

    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(userService)
            .passwordEncoder(passwordEncoder)
            .and()
            .build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring()
                .antMatchers("/css/**", "/js/**", "/images/**", "/h2-console/**")
        }
    }

}
