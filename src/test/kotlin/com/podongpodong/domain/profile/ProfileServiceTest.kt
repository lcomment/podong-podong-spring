package com.podongpodong.domain.profile

import com.podongpodong.domain.profile.dto.request.ProfileCreateRequest
import com.podongpodong.domain.profile.dto.request.ProfileUpdateRequest
import com.podongpodong.domain.profile.entity.Profile
import com.podongpodong.domain.profile.repository.ProfileRepository
import com.podongpodong.domain.profile.service.ProfileService
import com.podongpodong.domain.user.entity.User
import com.podongpodong.domain.user.entity.enums.Provider

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ProfileServiceTest {
    @InjectMocks
    private lateinit var profileService: ProfileService

    @Mock
    private lateinit var profileRepository: ProfileRepository

    private lateinit var profileCreateRequest: ProfileCreateRequest

    private lateinit var profileUpdateRequest: ProfileUpdateRequest

    private lateinit var user: User

    private lateinit var profile: Profile

    @BeforeEach
    fun setup() {
        val name = "Test Name"
        val bio = "Test Bio"
        val email = "Test@test.com"
        val picture = "Test Picture"

        profileCreateRequest = ProfileCreateRequest(name, bio, email, picture)
        profileUpdateRequest = ProfileUpdateRequest(name, bio, email, picture)
        user = User(Provider.GOOGLE, "1234", name, email)
        profile = Profile(user, name, bio, email, picture)
    }

    @AfterEach
    fun cleanup() {
        profileRepository.deleteAll()
    }

    @Test
    fun `새로운 프로필을 저장한다`() {
        // given
        given(profileRepository.save(any(Profile::class.java)))
            .willReturn(profile)

        // when
        val saveProfile: Profile = profileService.save(profileCreateRequest, user)

        // then
        assertEquals(profile, saveProfile)
    }

    @Test
    fun `이미 프로필이 있으면 그 프로필을 리턴한다`() {
        // given
        given(profileRepository.findByUser(any(User::class.java)))
            .willReturn(profile)

        // when
        val saveProfile: Profile = profileService.save(profileCreateRequest, user)

        // then
        assertEquals(profile, saveProfile)
    }

    @Test
    fun `프로필 수정에 성공한다`() {
        // given
        given(profileRepository.findByUser(any(User::class.java)))
            .willReturn(profile)

        // when
        val updateProfile: Profile = profileService.update(profileUpdateRequest, user)

        // then
        assertEquals(profile, updateProfile)
    }

    @Test
    fun `프로필 수정에 실패한다`() {
        // given
        given(profileRepository.findByUser(any(User::class.java)))
            .willReturn(null)

        // then
        assertThrows(NullPointerException::class.java) {
            profileService.update(profileUpdateRequest, user)
        }
    }

    @Test
    fun `프로필 조회에 성공한다`() {
        // given
        given(profileRepository.findByUser(any(User::class.java)))
            .willReturn(profile)

        // when
        val existProfile: Profile = profileService.findExistProfile(user)

        // then
        assertEquals(profile, existProfile)
    }

    @Test
    fun `프로필 조회에 실패한다`() {
        // given
        given(profileRepository.findByUser(any(User::class.java)))
            .willReturn(null)

        // then
        assertThrows(NullPointerException::class.java) {
            profileService.findExistProfile(user)
        }
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}