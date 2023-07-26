package com.podongpodong.global.jwt.filter

import com.podongpodong.global.jwt.util.JwtValidator
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtAuthenticationFilter(
    private val jwtValidator: JwtValidator
) : OncePerRequestFilter() {
    companion object {
        private const val AUTHORIZATION_TAG = "Authorization"
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String? = getTokensFromHeader(request)
        token?.let {
            val authentication: Authentication = jwtValidator.getAuthentication(it)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun getTokensFromHeader(request: HttpServletRequest): String? {
        return request.getHeader(AUTHORIZATION_TAG)
    }
}