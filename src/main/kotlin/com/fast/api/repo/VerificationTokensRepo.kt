package com.fast.api.repo

import com.fast.api.model.VerificationToken
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface VerificationTokensRepo : CrudRepository<VerificationToken, UUID> {

    @Query("SELECT * FROM verification_tokens WHERE token = ?1 LIMIT 1", nativeQuery = true)
    fun getToken(token: String): VerificationToken?

    @Modifying
    @Transactional
    @Query("DELETE FROM verification_tokens WHERE token = ?1", nativeQuery = true)
    fun deleteToken(token: String)

    @Modifying
    @Transactional
    @Query("DELETE FROM verification_tokens WHERE expires_at < ?1", nativeQuery = true)
    fun deleteExpired(now: Date)

}
