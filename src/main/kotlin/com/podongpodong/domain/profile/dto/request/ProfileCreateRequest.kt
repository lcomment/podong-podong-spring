package com.podongpodong.domain.profile.dto.request

import com.podongpodong.domain.profile.entity.Profile
import com.podongpodong.domain.user.entity.User

data class ProfileCreateRequest(
    val nickname: String?,
    val bio: String?,
    val email: String?,
    val picture: String?
) {
    fun toProfileEntity(user: User): Profile {
        return Profile(
            user = user,
            nickname = nickname ?: user.name,
            bio = bio,
            email = email ?: user.email,
            picture = picture
        )
    }
}
