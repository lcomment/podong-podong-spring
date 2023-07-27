package com.podongpodong.domain.profile.dto.response.enums

enum class ProfileResponseMessage(val msg: String) {
    CREATE_PROFILE_SUCCESS("프로필이 생성되었습니다."),
    ALREADY_EXIST_SUCCESS("이미 프로필이 존재합니다."),
    UPDATE_PROFILE_SUCCESS("프로필이 수정되었습니다."),
    FIND_USER_SUCCESS("프로필 조회에 성공하셨습니다."),
    FIND_USER_FAIL("프로필 조회에 실패하셨습니다.")

}