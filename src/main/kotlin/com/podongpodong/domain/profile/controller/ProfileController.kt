package com.podongpodong.domain.profile.controller

import com.podongpodong.domain.profile.dto.request.ProfileCreateRequest
import com.podongpodong.domain.profile.dto.request.ProfileUpdateRequest
import com.podongpodong.domain.profile.dto.response.ProfileDetailResponse
import com.podongpodong.domain.profile.dto.response.ProfileSimpleResponse
import com.podongpodong.domain.profile.dto.response.enums.ProfileResponseMessage
import com.podongpodong.domain.profile.service.ProfileService
import com.podongpodong.domain.user.entity.User
import com.podongpodong.global.common.ApiResponse
import com.podongpodong.global.common.annotation.AuthorizedUser
import com.podongpodong.global.common.createSuccessWithData

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/profiles")
class ProfileController(
    private val profileService: ProfileService
) {
    @PostMapping
    fun add(
        @AuthorizedUser user: User,
        @RequestBody request: ProfileCreateRequest
    ): ApiResponse<ProfileSimpleResponse> {
        return createSuccessWithData(
            ProfileResponseMessage.CREATE_PROFILE_SUCCESS.msg,
            profileService.save(request, user).toProfileSimpleResponse()
        )
    }

    @GetMapping
    fun detail(@AuthorizedUser user: User): ApiResponse<ProfileDetailResponse> {
        return createSuccessWithData(
            ProfileResponseMessage.FIND_USER_SUCCESS.msg,
            profileService.findExistProfile(user).toProfileDetailResponse()
        )
    }

    @PutMapping()
    fun modify(
        @AuthorizedUser user: User,
        @RequestBody request: ProfileUpdateRequest
    ): ApiResponse<ProfileSimpleResponse> {
        return createSuccessWithData(
            ProfileResponseMessage.UPDATE_PROFILE_SUCCESS.msg,
            profileService.update(request, user).toProfileSimpleResponse()
        )
    }
}