package com.fast.api.repo

import com.fast.api.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface UsersRepo : CrudRepository<User, UUID> {

    @Query("select * from users where id = ?1 and owner = true", nativeQuery = true)
    fun findOwnerById(id: UUID): User?

    @Query("select * from users where id = ?1 and owner = false", nativeQuery = true)
    fun findViewById(id: UUID): User?
}