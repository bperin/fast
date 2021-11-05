package com.fast.api.task

import com.fast.api.repo.VerificationTokensRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*


@Component
class DeleteExpiredTokens() {

    @Autowired
    private lateinit var verificationTokensRepo: VerificationTokensRepo

    /**
     * Expire jobs where start_time is in the past
     */
    @Scheduled(fixedRate = 600000)
    fun expireTokens() {
        verificationTokensRepo.deleteExpired(Date())
    }
}