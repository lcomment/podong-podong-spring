package com.podongpodong.domain.chat.dto.response

data class ChatMessageResponse(
    val roomCode: String,
    val writer: String,
    val message: String
)