package com.fast.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "users")
data class User(
    @Id
    @Type(type = "pg-uuid")
    val id: UUID = UUID.randomUUID()
) {

    @OneToMany(targetEntity = Cinema::class)
    var cinemas: MutableList<Cinema> = mutableListOf()

    @OneToMany(targetEntity = Rating::class)
    var ratings: MutableList<Rating> = mutableListOf()

    @Column(name = "email", unique = true, length = 50)
    @field:Schema(description = "email", type = "String", example = "bperin42@gmail.com")
    var email: String = ""

    @Column(name = "password", nullable = false)
    @field:Schema(description = "password", type = "String", example = "supersecret")
    @JsonIgnore
    var password: String = ""

    @Column(name = "owner", nullable = false, updatable = false)
    var owner: Boolean = false

    @Column(name = "admin", nullable = false, updatable = false)
    var admin: Boolean = false

    @Column(name = "verified", nullable = false)
    var verified: Boolean = false

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonProperty("created_at")
    val createdAt: Date = Date()

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    val updatedAt: Date = Date()

}
