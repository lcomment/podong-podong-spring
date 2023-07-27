package com.podongpodong.global.common.resovler

import com.podongpodong.domain.user.entity.User
import com.podongpodong.global.common.annotation.AuthorizedUser
import com.podongpodong.global.oauth2.dto.PrincipalUser

import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


@Component
class AuthorizedUserResolver: HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthorizedUser::class.java)
                && User::class.java.isAssignableFrom(parameter.parameterType)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val principalUser: PrincipalUser =
            SecurityContextHolder.getContext().authentication.principal as PrincipalUser;

        return principalUser.getUser()
    }
}