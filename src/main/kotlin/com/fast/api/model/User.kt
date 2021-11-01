package com.fast.api.model

import com.fasterxml.jackson.annotation.JsonIgnore
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

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Cinema::class)
    var cinemas: MutableList<Cinema> = mutableListOf()

    @Column(name = "email", unique = true, length = 50)
    @field:Schema(description = "email", type = "String", example = "bperin42@gmail.com")
    var email: String? = null

    @Column(name = "password", nullable = false)
    @field:Schema(description = "password", type = "String", example = "supersecret")
    @JsonIgnore
    var password: String = ""

    @Column(name = "owner", nullable = false)
    var owner: Boolean = false

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date()

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Date = Date()

}
