package com.podongpodong.domain.chat.entity

import com.podongpodong.domain.chat.dto.response.ChatMessageResponse
import com.podongpodong.domain.user.entity.User

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "chat_messages")
class ChatMessage(
    chatRoom: ChatRoom,
    user: User,
    content: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User = user

    @ManyToOne
    @JoinColumn(name = "code", nullable = false)
    val chatRoom: ChatRoom = chatRoom

    @Column(name = "message", nullable = false)
    val message: String = content

    fun toChatMessageResponse(): ChatMessageResponse {
        return ChatMessageResponse(chatRoom.code, user.name, message)
    }
}