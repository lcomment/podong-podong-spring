package com.podongpodong.domain.user.repository

import com.podongpodong.domain.user.entity.User

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByProviderId(providerId: String): User?;
}