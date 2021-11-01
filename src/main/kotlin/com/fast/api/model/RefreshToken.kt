package com.fast.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
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

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    var user: User? = null

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonProperty("created_at")
    val createdAt: Date = Date()

    @Column(name = "expires_at")
    @JsonProperty("expires_at")
    val expiresAt: Date = DateTime().plusYears(2).toDate()

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    val updatedAt: Date = Date()

}
