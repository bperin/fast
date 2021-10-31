package com.fast.api.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.*


@Entity
@Table(name = "movies")
data class Movie(
    @Id
    @Type(type = "pg-uuid")
    val id: UUID = UUID.randomUUID()
) {

    @OneToMany(fetch = FetchType.EAGER)
    var showTimes: MutableList<ShowTime> = mutableListOf()

    @Column(name = "title")
    @JsonProperty("title")
    @field:Schema(description = "title", type = "String", example = "The Fast and the Furious", nullable = false)
    var title: String = ""

    @Column(name = "idmb_id", nullable = false, unique = true)
    @SerializedName("imdb_id") //needed for Gson loading
    @JsonProperty("imdb_id")
    var imdbId: String = ""

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date()

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Date = Date()

}
