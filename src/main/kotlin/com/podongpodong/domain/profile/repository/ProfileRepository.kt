package com.podongpodong.domain.profile.repository

import com.podongpodong.domain.profile.entity.Profile
import com.podongpodong.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProfileRepository : JpaRepository<Profile, Long> {
    fun findByUser(user: User): Profile?
}