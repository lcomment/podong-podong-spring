package com.podongpodong.global.oauth2.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.podongpodong.global.jwt.dto.JwtToken
import com.podongpodong.global.jwt.util.JwtProvider
import com.podongpodong.global.oauth2.dto.PrincipalUser

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationSuccessHandler(
    private val jwtProvider: JwtProvider,
    private val objectMapper: ObjectMapper
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val principalUser: PrincipalUser = authentication.principal as PrincipalUser
        val jwtToken: JwtToken = jwtProvider.createJwtToken(principalUser)

        response.contentType = "application/json";
        response.writer.write(objectMapper.writeValueAsString(jwtToken))
    }
}