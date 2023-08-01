package com.podongpodong.domain.friends.service

import com.podongpodong.domain.chat.entity.ChatRoom
import com.podongpodong.domain.chat.entity.enums.ChatRoomStatus
import com.podongpodong.domain.chat.repository.ChatRoomRepository
import com.podongpodong.global.common.utils.CodeGenerator
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FriendsModeService(
    private val codeGenerator: CodeGenerator,
    private val chatRoomRepository: ChatRoomRepository
) {
    @Transactional
    fun save(): ChatRoom {
        val roomCode: String = generatedRoomCode()
        return chatRoomRepository.save(ChatRoom(roomCode))
    }

    @Transactional
    fun enter(roomCode: String): ChatRoom {
        val chatRoom: ChatRoom = findByRoomCode(roomCode)

        checkEnterAble(chatRoom)
        chatRoom.increase()
        return chatRoom
    }

    @Transactional(readOnly = true)
    fun findByRoomCode(roomCode: String): ChatRoom {
        return chatRoomRepository.findByCode(roomCode) ?: throw NullPointerException()
    }

    fun checkEnterAble(chatRoom: ChatRoom): Unit {
        if(chatRoom.headCount < 6 && chatRoom.status == ChatRoomStatus.CREATED) {
            return
        }
        throw NotFoundException()
    }

    fun generatedRoomCode(): String {
        return codeGenerator.generateCode()
    }
}