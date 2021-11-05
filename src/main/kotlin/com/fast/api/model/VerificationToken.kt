package com.fast.api.model

import org.apache.commons.lang3.RandomStringUtils
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.joda.time.DateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "verification_tokens")
data class VerificationToken(
    @Id
    @Type(type = "pg-uuid")
    val id: UUID = UUID.randomUUID(),
) {
    @Column(name = "token", unique = true, nullable = false, length = 500)
    var token: String = RandomStringUtils.randomAlphanumeric(32)

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date()

    @Column(name = "expires_at")
    val expiresAt: Date = DateTime().plusDays(1).toDate()
}
