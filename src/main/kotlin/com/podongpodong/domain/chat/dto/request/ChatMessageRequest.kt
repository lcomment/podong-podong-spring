package com.podongpodong.domain.chat.dto.request

data class ChatMessageRequest(
    val roomCode: String,
    var message: String
)
