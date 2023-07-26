package com.podongpodong.global.oauth2.service

import com.podongpodong.domain.user.entity.enums.Provider
import com.podongpodong.domain.user.service.UserService
import com.podongpodong.global.oauth2.converter.AttributeConverterFactory
import com.podongpodong.global.oauth2.converter.PrincipalUserConverter
import com.podongpodong.global.oauth2.dto.OAuth2Request
import org.springframework.security.oauth2.client.registration.ClientRegistration

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userService: UserService,
    private val converterFactory: AttributeConverterFactory,
    private val principalUserConverter: PrincipalUserConverter
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User: OAuth2User = super.loadUser(userRequest)
        val provider: Provider = getProvider(userRequest.clientRegistration)

        val oAuth2Request: OAuth2Request = converterFactory.getConverterByProvider(provider)?.run {
            this.toOAuth2Request(oAuth2User.attributes)
        }!!

        return principalUserConverter.toPrincipalUser(userService.saveIfNewUser(oAuth2Request))
    }

    private fun getProvider(clientRegistration: ClientRegistration): Provider {
        return Provider.valueOf(clientRegistration.clientName.uppercase())
    }
}