package com.fast.api.model

import com.fast.api.ext.getRandomString
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.joda.time.DateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "verification_tokens")
data class VerificationToken(
    @Id
    @Type(type = "pg-uuid")
    val id: UUID = UUID.randomUUID(),
) {
    @Column(name = "token", unique = true, nullable = false, length = 500)
    var token: String = ""
        set(value) {
            value.getRandomString(32)
            field = value
        }

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User::class)
    var user: User = User()

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date()

    @Column(name = "expires_at")
    val expiresAt: Date = DateTime().plusDays(1).toDate()
}
