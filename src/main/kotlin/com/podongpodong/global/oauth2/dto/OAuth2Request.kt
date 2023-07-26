package com.podongpodong.global.oauth2.dto

import com.podongpodong.domain.user.entity.User
import com.podongpodong.domain.user.entity.enums.Provider

data class OAuth2Request(
    val provider: Provider,
    val providerId: String,
    val name: String,
    val email: String?
) {
    fun toUserEntity(): User {
        return User(
            provider = provider,
            providerId = providerId,
            name = name,
            email = email
        )
    }
}
