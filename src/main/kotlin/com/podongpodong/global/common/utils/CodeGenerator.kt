package com.podongpodong.global.common.utils

import org.springframework.stereotype.Component

@Component
class CodeGenerator {
    companion object {
        private val LENGTH = 8
        private val AlphaNumericString = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz")
    }
    fun generateCode(): String {
        val oneTimePassword = StringBuilder(LENGTH)
        for (i in 0 until LENGTH) {
            val index = (AlphaNumericString.length * Math.random()).toInt()
            oneTimePassword.append(AlphaNumericString[index])
        }
        return oneTimePassword.toString().trim { it <= ' ' }
    }
}