package com.fast.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "cinemas")
data class Cinema(
    @Id
    @Type(type = "pg-uuid")
    val id: UUID = UUID.randomUUID()
) {

    @OneToMany(fetch = FetchType.EAGER, targetEntity = ShowTime::class)
    @JsonProperty("show_times")
    var showTimes: MutableList<ShowTime> = mutableListOf()

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonProperty("updated_at")
    val createdAt: Date = Date()

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    val updatedAt: Date = Date()

}
