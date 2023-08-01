package com.podongpodong.domain.chat.service

import com.podongpodong.domain.chat.dto.param.ChatMessageParam
import com.podongpodong.domain.chat.entity.ChatMessage
import com.podongpodong.domain.chat.repository.ChatMessageRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatMessageService(
    private val chatMessageRepository: ChatMessageRepository
) {
    @Transactional
    fun save(param: ChatMessageParam): ChatMessage {
        return chatMessageRepository.save(param.toChatMessageEntity())
    }
}