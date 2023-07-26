package com.podongpodong.global.jwt.util

import com.podongpodong.domain.user.entity.User
import com.podongpodong.domain.user.service.UserService
import com.podongpodong.global.oauth2.converter.PrincipalUserConverter
import com.podongpodong.global.oauth2.dto.PrincipalUser

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

import java.security.Key

@Component
class JwtValidator(
    private val key: Key,
    private val userService: UserService,
    private val principalUserConverter: PrincipalUserConverter
) {
    fun getAuthentication(accessToken: String): Authentication {
        val claims = getTokenClaims(accessToken)
        val user: User = userService.findById(claims.get("id", String::class.java).toLong())
        val principalUser: PrincipalUser = principalUserConverter.toPrincipalUser(user)

        return UsernamePasswordAuthenticationToken(principalUser, "", principalUser.authorities)
    }

    private fun getTokenClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}
