package com.fast.api.model

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Entity
@Table(name = "movies")
data class Movie(
    @Id
    @Type(type = "pg-uuid")
    val id: UUID = UUID.randomUUID()
) {

    @Column(name = "title")
    @field:Schema(description = "title", type = "String", example = "The Fast and the Furious", nullable = false)
    var title: String = ""

    @Column(name = "idmb_id", nullable = false)
    var imdbId: String = ""

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date()

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Date = Date()

}
