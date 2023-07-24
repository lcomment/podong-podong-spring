package com.podongpodong.global.audit

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

import jakarta.persistence.Embeddable

import java.time.LocalDateTime

@Embeddable
open class AuditEntity {
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set

    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
        protected set
}