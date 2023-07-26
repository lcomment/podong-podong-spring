package com.podongpodong.global.config.security

import com.podongpodong.global.oauth2.service.CustomOAuth2UserService
import com.podongpodong.global.oauth2.service.CustomOidcUserService

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler


@Configuration
@EnableWebSecurity
class SecurityConfig(
    val oAuth2UserService: CustomOAuth2UserService,
    val oidcUserService: CustomOidcUserService,
    val successHandler: AuthenticationSuccessHandler
) {
    @Bean
    fun oauth2SecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic { basic -> basic.disable() }
            .csrf { csrf -> csrf.disable() }
            .formLogin { form -> form.disable() }
            .headers { header -> header.frameOptions { frameOptions -> frameOptions.disable() } }
            .sessionManagement { setSessionManagement() }
            .authorizeHttpRequests(setAuthorizeHttpRequests())
            .oauth2Login(setOAuth2Config())
            .build()
    }

    private fun setSessionManagement(): Customizer<SessionManagementConfigurer<HttpSecurity>> =
        Customizer { config: SessionManagementConfigurer<HttpSecurity> ->
            config.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
            )
        }

    private fun setAuthorizeHttpRequests(): Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> =
        Customizer { requests ->
            requests
                .requestMatchers("/auth2/**", "/login/**").permitAll()
                .requestMatchers("/docs/api-docs.html").permitAll()
                .requestMatchers("/api/v1/**").hasRole("MEMBER")
                .anyRequest().authenticated()
        }

    private fun setOAuth2Config(): Customizer<OAuth2LoginConfigurer<HttpSecurity>> =
        Customizer { oauth ->
            oauth.authorizationEndpoint { o -> o.baseUri("/auth2/authorize") }
                .userInfoEndpoint { endpoint ->
                    endpoint.userService(oAuth2UserService)
                        .oidcUserService(oidcUserService)
                }
                .successHandler(successHandler)
        }
}