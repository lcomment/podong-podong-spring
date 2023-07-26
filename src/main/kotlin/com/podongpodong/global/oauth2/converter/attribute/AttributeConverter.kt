package com.podongpodong.global.oauth2.converter.attribute

import com.podongpodong.global.oauth2.dto.OAuth2Request

interface AttributeConverter {
    fun toOAuth2Request(attributes: MutableMap<String, Any>): OAuth2Request;
}