package com.podongpodong.global.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.Key

@Configuration
class JwtConfig {
    @Value("\${jwt.secret}")
    private val jwtSecret: String? = null

    @Bean
    fun key(): Key {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }
}