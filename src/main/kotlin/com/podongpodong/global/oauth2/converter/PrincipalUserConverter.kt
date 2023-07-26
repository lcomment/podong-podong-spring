package com.podongpodong.global.oauth2.converter

import com.podongpodong.domain.user.entity.User
import com.podongpodong.global.oauth2.dto.PrincipalUser
import org.springframework.stereotype.Component

@Component
class PrincipalUserConverter {
    fun toPrincipalUser(user: User): PrincipalUser {
        return PrincipalUser(
            user,
            mutableMapOf("id" to user.id!!),
            user.getAuthorities()
        )
    }
}