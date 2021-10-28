package com.fast.api.model

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

    @OneToOne(fetch = FetchType.EAGER, targetEntity = Movie::class)
    lateinit var movie: Movie

    @Timestamp
    var startTime = Date()

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date()

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Date = Date()

}
