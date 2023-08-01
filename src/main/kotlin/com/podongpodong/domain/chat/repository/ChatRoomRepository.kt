package com.podongpodong.domain.chat.repository

import com.podongpodong.domain.chat.entity.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
    fun findByCode(code: String): ChatRoom?
}