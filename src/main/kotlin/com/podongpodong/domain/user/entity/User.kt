package com.podongpodong.domain.user.entity

import com.podongpodong.domain.user.entity.enums.Provider
import com.podongpodong.domain.user.entity.enums.Role
import com.podongpodong.global.audit.AuditEntity
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity(name = "users")
class User(
    provider: Provider,
    providerId: String,
    name: String,
    email: String?
) {
    @Id
    val id: Long? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    val provider: Provider = provider

    @Column(name = "provider_id", nullable = false)
    val providerId: String = providerId

    @Column(name = "name", nullable = false)
    val name: String = name

    @Column(name = "email")
    val email: String? = email

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role")
    val role: MutableList<Role> = mutableListOf(Role.ROLE_MEMBER)

    @Embedded
    var auditEntity: AuditEntity = AuditEntity()

    fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return role.stream()
            .map { SimpleGrantedAuthority(it.name) }
            .toList()
    }
}
