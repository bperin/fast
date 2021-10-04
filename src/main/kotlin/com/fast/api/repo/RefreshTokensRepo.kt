package com.fast.api.repo

import com.fast.api.model.RefreshToken
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * Repo to handle refresh tokens this is where we may want to take away perm access
 * from a user or simply refresh
 */
@Repository
interface RefreshTokensRepo : CrudRepository<RefreshToken, UUID> {

    @Query("SELECT * FROM refresh_tokens WHERE token = ?1 LIMIT 1", nativeQuery = true)
    fun getRefreshToken(token: String): RefreshToken?
}