package com.fast.api.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.joda.time.DateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "refresh_tokens")
data class RefreshToken(
    @Id
    @Type(type = "pg-uuid")
    val id: UUID = UUID.randomUUID()
) {

    @Column(name = "token", unique = true, nullable = false, columnDefinition = "text")
    var token: String? = null

    @OneToOne(fetch = FetchType.LAZY, optional = true, targetEntity = User::class)
    var user: User? = null

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date()

    @Column(name = "expires_at")
    val expiresAt: Date = DateTime().plusYears(2).toDate()

}
