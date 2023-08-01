package com.podongpodong.domain.friends.controller

import com.podongpodong.domain.chat.entity.ChatRoom
import com.podongpodong.domain.friends.dto.response.ChatRoomResponse
import com.podongpodong.domain.friends.dto.response.enums.FriendsModeResponseMessage
import com.podongpodong.domain.friends.service.FriendsModeService
import com.podongpodong.domain.user.entity.User
import com.podongpodong.global.common.ApiResponse
import com.podongpodong.global.common.annotation.AuthorizedUser
import com.podongpodong.global.common.createSuccessWithData

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/multi/friends")
class FriendsModeController(
    private val friendsModeService: FriendsModeService
) {

    @PostMapping("")
    fun createFriendsRoom(@AuthorizedUser user: User): ApiResponse<ChatRoomResponse> {
        val chatRoom: ChatRoom = friendsModeService.save()
        user.updateRoom(chatRoom)

        return createSuccessWithData(
            FriendsModeResponseMessage.CREATE_POMODORO_FRIENDS_ROOM.message,
            ChatRoomResponse(chatRoom.code)
        )
    }

    @GetMapping("")
    fun enterFriendsRoom(@AuthorizedUser user: User,
                         @RequestParam code: String): ApiResponse<ChatRoomResponse> {
        return createSuccessWithData(
            FriendsModeResponseMessage.ENTER_POMODORO_FRIENDS_ROOM.message,
            ChatRoomResponse(friendsModeService.enter(code).code)
        )
    }
}