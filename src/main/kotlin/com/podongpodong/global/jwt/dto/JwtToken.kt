package com.podongpodong.global.jwt.dto

data class JwtToken(val accessToken: String, val refreshToken: String, val grantType: String)
