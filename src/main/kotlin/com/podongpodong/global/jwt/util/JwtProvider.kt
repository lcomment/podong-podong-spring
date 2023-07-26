package com.podongpodong.global.jwt.util

import com.podongpodong.global.jwt.dto.JwtToken
import com.podongpodong.global.oauth2.dto.PrincipalUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtProvider(
    private val key: Key
) {
    companion object {
        private const val ACCESS_TOKEN_VALIDATION_SECOND = 60L * 60 * 24 * 1000
        private const val REFRESH_TOKEN_VALIDATION_SECOND = 60L * 60 * 24 * 1000 * 14
        private const val BEARER_TYPE = "bearer"
    }

    fun createJwtToken(principalUser: PrincipalUser): JwtToken {
        val claims = getClaims(principalUser)
        val accessToken = getToken(principalUser, claims, ACCESS_TOKEN_VALIDATION_SECOND)
        val refreshToken = getToken(principalUser, claims, REFRESH_TOKEN_VALIDATION_SECOND)

        return JwtToken(accessToken, refreshToken, BEARER_TYPE)
    }

    fun getClaims(principalUser: PrincipalUser): Claims {
        val claims = Jwts.claims()
        claims["id"] = principalUser.name
        return claims
    }

    private fun getToken(
        principalUser: PrincipalUser,
        claims: Claims,
        validationSecond: Long
    ): String {
        val now = Date().time
        return Jwts.builder()
            .setSubject(principalUser.name)
            .setClaims(claims)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(Date(now + validationSecond))
            .compact()
    }
}