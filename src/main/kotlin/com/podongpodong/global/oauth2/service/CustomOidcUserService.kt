package com.podongpodong.global.oauth2.service

import com.podongpodong.domain.user.entity.enums.Provider
import com.podongpodong.domain.user.service.UserService
import com.podongpodong.global.oauth2.converter.AttributeConverterFactory
import com.podongpodong.global.oauth2.converter.PrincipalUserConverter
import com.podongpodong.global.oauth2.dto.OAuth2Request

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

@Service
class CustomOidcUserService(
    private val userService: UserService,
    private val converterFactory: AttributeConverterFactory,
    private val principalUserConverter: PrincipalUserConverter
) : OidcUserService() {

    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val provider: Provider = getProvider(userRequest.clientRegistration)
        val oidcUser: OidcUser = super.loadUser(getNewOidcUserRequest(userRequest))

        val oAuth2Request: OAuth2Request = converterFactory.getConverterByProvider(provider)?.run {
            this.toOAuth2Request(oidcUser.attributes)
        }!!

        return principalUserConverter.toPrincipalUser(userService.saveIfNewUser(oAuth2Request))
    }

    private fun getProvider(clientRegistration: ClientRegistration): Provider {
        return Provider.valueOf(clientRegistration.clientName.uppercase())
    }

    private fun getNewOidcUserRequest(userRequest: OidcUserRequest): OidcUserRequest {
        val registration: ClientRegistration =
            ClientRegistration.withClientRegistration(userRequest.clientRegistration)
                .userNameAttributeName("sub")
                .build()

        return OidcUserRequest(registration, userRequest.accessToken, userRequest.idToken)
    }
}