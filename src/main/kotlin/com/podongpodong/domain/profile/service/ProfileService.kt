package com.podongpodong.domain.profile.service

import com.podongpodong.domain.profile.dto.request.ProfileCreateRequest
import com.podongpodong.domain.profile.dto.request.ProfileUpdateRequest
import com.podongpodong.domain.profile.entity.Profile
import com.podongpodong.domain.profile.repository.ProfileRepository
import com.podongpodong.domain.user.entity.User

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.util.Objects.isNull

@Service
class ProfileService(
    private val profileRepository: ProfileRepository
) {
    @Transactional
    fun save(profileCreateRequest: ProfileCreateRequest, user: User): Profile {
        val existProfile: Profile? = findByUser(user)

        return if(isNull(existProfile)){
            profileRepository.save(profileCreateRequest.toProfileEntity(user))
        } else {
            existProfile!!
        }
    }

    @Transactional
    fun update(request: ProfileUpdateRequest, user: User): Profile {
        return findExistProfile(user).update(request)
    }

    @Transactional(readOnly = true)
    fun findExistProfile(user: User): Profile {
        return findByUser(user) ?: throw NullPointerException()
    }

    @Transactional(readOnly = true)
    fun findByUser(user: User): Profile? {
        return profileRepository.findByUser(user)
    }
}
