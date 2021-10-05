package com.fast.api.model

import io.swagger.v3.oas.annotations.media.Schema
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import org.joda.time.DateTime
import org.joda.time.Period
import java.util.*
import javax.persistence.*
import kotlin.jvm.Transient


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

    @Column(name = "first", length = 50)
    @field:Schema(description = "first name", type = "String", example = "Brian")
    var first: String? = null

    @Column(name = "last", length = 50)
    @field:Schema(description = "last name", type = "String", example = "Perin")
    var last: String? = null

    @Column(name = "number", unique = true, nullable = true, updatable = true)
    var number: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "birthday")
    var birthday: Date? = null

    @Column(name = "fcm_token", nullable = true)
    var fcmToken: String? = null

    @Column(name = "blocked", nullable = false)
    var blocked = false

    @Column(name = "strikes", nullable = false)
    var strikes: Int = 0

    @Column(name = "push_enabled", nullable = false)
    var pushEnabled = false

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Date = Date()

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Date = Date()

    @Transient
    var age: Int = -1
        get() {
            birthday?.let {
                val period = Period(DateTime(birthday), DateTime())
//                return period.years.toString() + "." + period.months.toString()
                return period.years
            }
            return -1
        }
}
