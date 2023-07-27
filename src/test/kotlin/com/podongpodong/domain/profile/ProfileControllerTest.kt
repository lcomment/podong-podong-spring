package com.podongpodong.domain.profile

import com.podongpodong.docs.ApiDocumentUtils.getDocumentRequest
import com.podongpodong.docs.ApiDocumentUtils.getDocumentResponse
import com.podongpodong.docs.RestDocsTest
import com.podongpodong.domain.profile.controller.ProfileController
import com.podongpodong.domain.profile.dto.request.ProfileCreateRequest
import com.podongpodong.domain.profile.dto.request.ProfileUpdateRequest
import com.podongpodong.domain.profile.entity.Profile
import com.podongpodong.domain.profile.service.ProfileService
import com.podongpodong.domain.user.entity.User
import com.podongpodong.domain.user.entity.enums.Provider
import com.podongpodong.domain.user.service.UserService
import com.podongpodong.global.jwt.util.JwtValidator
import com.podongpodong.global.oauth2.dto.PrincipalUser

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProfileController::class)
class ProfileControllerTest : RestDocsTest() {
    @MockBean
    private lateinit var profileService: ProfileService

    @MockBean
    private lateinit var userService: UserService

    @MockBean
    private lateinit var jwtValidator: JwtValidator

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

        val principalUser = PrincipalUser(user, mutableMapOf(), mutableListOf())
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
            principalUser, principalUser.password, principalUser.authorities
        )
    }

    @Test
    fun `프로필을 생성한다`() {
        // given
        given(profileService.save(any(ProfileCreateRequest::class.java), any(User::class.java)))
            .willReturn(profile)

        // when
        val perform: ResultActions = this.mockMvc.perform(
            post("/api/v1/profiles")
                .content(toJson(profileCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer accesstoken")
                .header("Refresh", "Bearer refreshtoken")
        )

        // then
        perform.andExpect(status().isOk)
            .andDo(
                document(
                    "profile-add",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("access token"),
                        headerWithName("Refresh").description("refresh token")
                    ),
                    requestFields(
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                        fieldWithPath("bio").type(JsonFieldType.STRING).description("소개"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("picture").type(JsonFieldType.STRING).description("사진")
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                            .description("닉네임"),
                        fieldWithPath("data.point").type(JsonFieldType.NUMBER).description("점수")
                    )
                )
            )
    }

    @Test
    fun `프로필을 조회한다`() {
        // given
        given(profileService.findExistProfile(any(User::class.java)))
            .willReturn(profile)

        // when
        val perform: ResultActions = this.mockMvc.perform(
            get("/api/v1/profiles")
                .content(toJson(profileCreateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer accesstoken")
                .header("Refresh", "Bearer refreshtoken")
        )

        // then
        perform.andExpect(status().isOk)
            .andDo(
                document(
                    "profile-detail",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                            .description("닉네임"),
                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("data.bio").type(JsonFieldType.STRING).description("소개"),
                        fieldWithPath("data.point").type(JsonFieldType.NUMBER).description("점수"),
                        fieldWithPath("data.picture").type(JsonFieldType.STRING).description("사진"),
                    )
                )
            )
    }

    @Test
    fun `프로필을 수한다`() {
        // given
        given(profileService.update(any(ProfileUpdateRequest::class.java), any(User::class.java)))
            .willReturn(profile)

        // when
        val perform: ResultActions = this.mockMvc.perform(
            put("/api/v1/profiles")
                .content(toJson(profileUpdateRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer accesstoken")
                .header("Refresh", "Bearer refreshtoken")
        )

        // then
        perform.andExpect(status().isOk)
            .andDo(
                document(
                    "profile-modify",
                    getDocumentRequest(),
                    getDocumentResponse(),
                    requestHeaders(
                        headerWithName("Authorization").description("access token"),
                        headerWithName("Refresh").description("refresh token")
                    ),
                    requestFields(
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                        fieldWithPath("bio").type(JsonFieldType.STRING).description("소개"),
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("picture").type(JsonFieldType.STRING).description("사진")
                    ),
                    responseFields(
                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                        fieldWithPath("data.nickname").type(JsonFieldType.STRING)
                            .description("닉네임"),
                        fieldWithPath("data.point").type(JsonFieldType.NUMBER).description("점수")
                    )
                )
            )
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}