package com.podongpodong.domain.chat.entity

import com.podongpodong.domain.chat.entity.enums.ChatRoomStatus

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Transient

@Entity(name = "chat_rooms")
class ChatRoom(
    code: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "code")
    val code: String = code

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: ChatRoomStatus = ChatRoomStatus.CREATED

    @Transient
    var headCount: Int = 1

    fun increase(): ChatRoom {
        headCount++
        return this
    }
}