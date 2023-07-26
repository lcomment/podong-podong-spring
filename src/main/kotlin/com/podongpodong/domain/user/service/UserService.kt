package com.podongpodong.domain.user.service

import com.podongpodong.domain.user.entity.User
import com.podongpodong.domain.user.repository.UserRepository
import com.podongpodong.global.oauth2.dto.OAuth2Request

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    val userRepository: UserRepository
) {
    @Transactional
    fun saveIfNewUser(oAuth2Request: OAuth2Request): User {
        return findByProviderId(oAuth2Request.providerId) ?: save(oAuth2Request)
    }

    @Transactional
    fun save(oAuth2Request: OAuth2Request): User {
        return userRepository.save(oAuth2Request.toUserEntity());
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw NullPointerException()
    }

    @Transactional(readOnly = true)
    fun findByProviderId(providerId: String): User? {
        return userRepository.findByProviderId(providerId)
    }
}