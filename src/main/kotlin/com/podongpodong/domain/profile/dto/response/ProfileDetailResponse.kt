package com.podongpodong.domain.profile.dto.response

data class ProfileDetailResponse(
    val nickname: String,
    val email: String?,
    val bio: String?,
    val point: Long,
    val picture: String?
)
