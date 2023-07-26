package com.podongpodong.global.oauth2.converter.attribute

import com.podongpodong.domain.user.entity.enums.Provider
import com.podongpodong.global.oauth2.dto.OAuth2Request
import org.springframework.stereotype.Component

@Component
class KakaoAttributeConverter : AttributeConverter {
    override fun toOAuth2Request(attributes: MutableMap<String, Any>): OAuth2Request {
        val providerId: String = attributes["sub"].toString()
        val name: String = attributes["nickname"].toString()
        val email: String? = attributes["email"]?.run { this.toString() }

        return OAuth2Request(Provider.KAKAO, providerId, name, email)
    }
}