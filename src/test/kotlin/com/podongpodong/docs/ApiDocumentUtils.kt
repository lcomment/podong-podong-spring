package com.podongpodong.docs

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors.*

interface ApiDocumentUtils {
    fun getDocumentRequest(): OperationRequestPreprocessor {
        return preprocessRequest(prettyPrint())
    }

    fun getDocumentResponse(): OperationResponsePreprocessor {
        return preprocessResponse(prettyPrint())
    }
}
