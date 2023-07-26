package com.podongpodong.global.common

data class ApiResponse<T>(val status: String,
                          val message: String,
                          val data: T?)

const val SUCCESS_STATUS: String = "SUCCESS"
const val FAIL_STATUS: String = "FAIL"

fun <T> createSuccessWithData(message: String, data: T?= null): ApiResponse<T> {
    return ApiResponse<T>(SUCCESS_STATUS, message, data)
}

fun <T> createSuccess(message: String): ApiResponse<T> {
    return createSuccessWithData(message)
}

fun <T> createFailWithData(message: String, data: T?= null): ApiResponse<T> {
    return ApiResponse<T>(FAIL_STATUS, message, data)
}

fun <T> createFail(message: String): ApiResponse<T> {
    return createFailWithData(message)
}
