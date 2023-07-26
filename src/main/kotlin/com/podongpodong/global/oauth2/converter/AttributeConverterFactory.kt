package com.podongpodong.global.oauth2.converter

import com.podongpodong.domain.user.entity.enums.Provider
import com.podongpodong.global.oauth2.converter.attribute.AttributeConverter
import com.podongpodong.global.oauth2.converter.attribute.GoogleAttributeConverter
import com.podongpodong.global.oauth2.converter.attribute.KakaoAttributeConverter

import org.springframework.stereotype.Component

import java.util.EnumMap

@Component
class AttributeConverterFactory(
    private val kakaoAttributeConverter: KakaoAttributeConverter,
    private val googleAttributeConverter: GoogleAttributeConverter
) {
    private val converterMap: MutableMap<Provider, AttributeConverter> = EnumMap(Provider::class.java)

    init {
        initialize()
    }

    private fun initialize() {
        converterMap[Provider.GOOGLE] = googleAttributeConverter
        converterMap[Provider.KAKAO] = kakaoAttributeConverter
    }

    fun getConverterByProvider(provider: Provider): AttributeConverter? {
        return converterMap[provider]
    }
}