package com.podongpodong.domain.profile.entity

import com.podongpodong.domain.profile.dto.request.ProfileUpdateRequest
import com.podongpodong.domain.profile.dto.response.ProfileDetailResponse
import com.podongpodong.domain.profile.dto.response.ProfileSimpleResponse
import com.podongpodong.domain.user.entity.User
import jakarta.persistence.*

@Entity(name = "profiles")
class Profile(
    user: User,
    nickname: String,
    bio: String?,
    email: String?,
    picture: String?
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User = user

    @Column(name = "nickname", nullable = false)
    var nickname: String = nickname

    @Column(name = "bio")
    var bio: String? = bio

    @Column(name = "email")
    var email: String? = email ?: user.email

    @Column(name = "point")
    var point: Long = 0

    @Column(name = "picture")
    var picture: String? = picture

    fun update(request: ProfileUpdateRequest): Profile {
        request.nickname?.let { nickname = this.nickname }
        request.email?.let { email = this.email }
        request.bio?.let { bio = this.bio }
        request.picture?.let { picture = this.picture }

        return this
    }

    fun toProfileSimpleResponse(): ProfileSimpleResponse {
        return ProfileSimpleResponse(nickname, point)
    }

    fun toProfileDetailResponse(): ProfileDetailResponse {
        return ProfileDetailResponse(nickname, email, bio, point, picture)
    }
}