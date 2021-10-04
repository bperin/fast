package com.fast.api.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.security.SecureRandom

object BcryptUtil {

    fun hashPassword(unencryptedPassword: String): String {
        val strength = 15
        val bCryptPasswordEncoder = BCryptPasswordEncoder(strength, SecureRandom())
        return bCryptPasswordEncoder.encode(unencryptedPassword)
    }

    fun checkPassword(unencryptedPassword: String, encryptedPassword: String): Boolean {
        val strength = 15
        val bCryptPasswordEncoder = BCryptPasswordEncoder(strength, SecureRandom())
        return bCryptPasswordEncoder.matches(unencryptedPassword, encryptedPassword)
    }
}