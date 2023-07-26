package com.podongpodong.domain.user.service

import com.podongpodong.domain.user.entity.User
import com.podongpodong.domain.user.entity.enums.Provider
import com.podongpodong.domain.user.repository.UserRepository
import com.podongpodong.global.oauth2.dto.OAuth2Request

import jakarta.persistence.EntityNotFoundException
import org.junit.jupiter.api.AfterEach

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.util.ReflectionTestUtils

import java.util.Optional

@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @InjectMocks
    private lateinit var userService: UserService

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var oAuth2Request: OAuth2Request

    private lateinit var mockUser: User

    private val mockId: Long = 1L

    private val mockProvider: Provider = Provider.GOOGLE

    private val mockProviderId: String = "testId"

    private val mockName: String = "test"

    private val mockEmail: String = "test@google.com"

    @BeforeEach
    fun setup() {
        oAuth2Request = OAuth2Request(mockProvider, mockProviderId, mockName, mockEmail)
        mockUser = User(mockProvider, mockProviderId, mockName, mockEmail)
    }

    @AfterEach
    fun cleanup() {
        userRepository.deleteAll()
    }

    @Test
    fun `유저를 등록한다`() {
        // given
        given(userRepository.save(any(User::class.java)))
            .willReturn(mockUser)

        // when
        val user: User = userService.save(oAuth2Request)

        // then
        assertEquals(user, mockUser)
    }

    @Test
    fun `Provider Id로 유저를 조회한다`() {
        // given
        given(userRepository.findByProviderId(anyString()))
            .willReturn(mockUser)

        // when
        val user: User? = userService.findByProviderId(mockProviderId)

        // then
        assertEquals(user, mockUser)
    }

    @Test
    fun `Id로 유저를 조회 성공한다`() {
        // given
        ReflectionTestUtils.setField(mockUser, "id", mockId)
        given(userRepository.findById(anyLong()))
            .willReturn(Optional.of(mockUser))

        // when
        val user: User = userService.findById(mockId)

        // then
        assertEquals(user, mockUser)
    }

    @Test
    fun `Id로 유저를 조회 실패한다`() {
        // given
        given(userRepository.findById(anyLong()))
            .willReturn(null)

        // then
        assertThrows(NullPointerException::class.java) {
            userService.findById(mockId)
        }
    }

    @Test
    fun `새로운 유저면 등록한다`() {
        // given
        given(userRepository.save(any(User::class.java)))
            .willReturn(mockUser)
        given(userRepository.findByProviderId(anyString()))
            .willReturn(null)

        // when
        val user: User = userService.saveIfNewUser(oAuth2Request)

        // then
        assertEquals(user, mockUser)
    }

    @Test
    fun `새로운 유저가 아니면 조회한다`() {
        // given
        given(userRepository.findByProviderId(anyString()))
            .willReturn(mockUser)

        // when
        val user: User = userService.saveIfNewUser(oAuth2Request)

        // then
        assertEquals(user, mockUser)
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}