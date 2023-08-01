package com.podongpodong.domain.chat.service

import com.podongpodong.domain.chat.entity.ChatRoom
import com.podongpodong.domain.chat.repository.ChatRoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository
) {
    @Transactional(readOnly = true)
    fun findByRoomCode(roomCode: String): ChatRoom {
        return chatRoomRepository.findByCode(roomCode) ?: throw NullPointerException()
    }
}