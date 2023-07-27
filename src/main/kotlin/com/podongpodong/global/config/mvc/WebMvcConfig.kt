package com.podongpodong.global.config.mvc

import com.podongpodong.domain.user.service.UserService
import com.podongpodong.global.common.resovler.AuthorizedUserResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(AuthorizedUserResolver())
    }
}