package com.podongpodong.domain.chat.controller

import com.podongpodong.domain.chat.dto.param.ChatMessageParam
import com.podongpodong.domain.chat.dto.request.ChatMessageRequest
import com.podongpodong.domain.chat.dto.response.ChatMessageResponse
import com.podongpodong.domain.chat.service.ChatMessageService
import com.podongpodong.domain.chat.service.ChatRoomService
import com.podongpodong.domain.user.entity.User
import com.podongpodong.global.common.annotation.AuthorizedUser

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/multi")
class ChatController(
    private val template: SimpMessagingTemplate,
    private val chatRoomService: ChatRoomService,
    private val chatMessageService: ChatMessageService
) {

    @MessageMapping("/chat/message")
    fun message(
        @AuthorizedUser user: User,
        request: ChatMessageRequest
    ) {
        val chatMessageParam = ChatMessageParam(
            room = chatRoomService.findByRoomCode(request.roomCode),
            user = user,
            content = request.message
        )

        val chatMessage: ChatMessageResponse =
            chatMessageService.save(chatMessageParam).toChatMessageResponse()

        template.convertAndSend("/sub/chat/room/${chatMessage.roomCode}", chatMessage)
    }
}