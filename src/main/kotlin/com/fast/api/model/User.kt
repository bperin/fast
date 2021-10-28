package com.fast.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import org.joda.time.DateTime
import org.joda.time.Period
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "users")
data class User(
    @Id
    @Type(type = "pg-uuid")
    val id: UUID = UUID.randomUUID()
) {

    @Column(name = "username", unique = true, length = 50)
    @field:Schema(description = "username", type = "String", example = "Briguy")
    var username: String? = null

    @Column(name = "password", nullable = false)
    @JsonIgnore
    var password: String = ""

    @Column(name = "owner", nullable = false)
    var available: Boolean = false

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date()

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Date = Date()

}
