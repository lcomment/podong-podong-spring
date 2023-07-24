package com.podongpodong.docs

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

import org.mockito.MockitoAnnotations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@Import(RestDocsConfiguration::class)
@AutoConfigureRestDocs
@WebMvcTest
open class RestDocsTest {
    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setMockMvc(
        context: WebApplicationContext,
        provider: RestDocumentationContextProvider
    ) {
        MockitoAnnotations.openMocks(this)

        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(
                documentationConfiguration(provider)
                    .uris()
                    .withScheme("http")
                    .withHost("127.0.0.1")
                    .withPort(8080)
            )
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(print())
            .alwaysDo<DefaultMockMvcBuilder>(document("api/v1"))
            .build()
    }

    fun toJson(value: Any): String {
        return objectMapper.writeValueAsString(value)
    }
}