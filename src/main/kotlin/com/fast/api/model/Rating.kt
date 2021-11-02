package com.fast.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "ratings")
data class Rating(
    @Id
    @Type(type = "pg-uuid")
    val id: UUID = UUID.randomUUID()
) {
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User::class)
    @OrderColumn
    var user: User? = null

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Movie::class)
    @OrderColumn
    var movie: Movie? = null

    @Column(name = "description", nullable = true)
    var description: String = ""

    @Column(name = "rating", nullable = false)
    var rating: Double = 0.0

    @JsonProperty("created_at")
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date()

    @JsonProperty("updated_at")
    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Date = Date()
}
