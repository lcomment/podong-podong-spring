package com.podongpodong.domain.profile.dto.request

data class ProfileUpdateRequest(
    val nickname: String?,
    val email: String?,
    val bio: String?,
    val picture: String?
) {
}