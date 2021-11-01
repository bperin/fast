package com.fast.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import jdk.jfr.Timestamp
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "showtimes")
data class ShowTime(
    @Id
    @Type(type = "pg-uuid")
    val id: UUID = UUID.randomUUID()
) {

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Movie::class)
    var movie: Movie = Movie()

    @Timestamp
    @JsonProperty("start_time")
    var startTime = Date()

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonProperty("created_at")
    val createdAt: Date = Date()

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    val updatedAt: Date = Date()

}
