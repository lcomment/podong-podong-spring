package com.podongpodong.domain.chat.dto.param

import com.podongpodong.domain.chat.entity.ChatMessage
import com.podongpodong.domain.chat.entity.ChatRoom
import com.podongpodong.domain.user.entity.User

data class ChatMessageParam(
    private val room: ChatRoom,
    private val user: User,
    private val content: String
) {
    fun toChatMessageEntity(): ChatMessage {
        return ChatMessage(
            chatRoom = room,
            user = user,
            content = content
        )
    }
}
