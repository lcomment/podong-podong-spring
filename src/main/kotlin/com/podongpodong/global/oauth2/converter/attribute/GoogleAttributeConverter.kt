package com.podongpodong.global.oauth2.converter.attribute

import com.podongpodong.domain.user.entity.enums.Provider
import com.podongpodong.global.oauth2.dto.OAuth2Request
import org.springframework.stereotype.Component

@Component
class GoogleAttributeConverter : AttributeConverter {
    override fun toOAuth2Request(attributes: MutableMap<String, Any>): OAuth2Request {
        val providerId: String = attributes["sub"] as String
        val name: String = attributes["name"] as String
        val email: String = attributes["email"] as String

        return OAuth2Request(Provider.GOOGLE, providerId, name, email)
    }
}