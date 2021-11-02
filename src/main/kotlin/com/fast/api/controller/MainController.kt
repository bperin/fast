package com.fast.api.controller

import com.fast.api.repo.UsersRepo
import com.fast.api.security.TokenService
import com.fast.api.security.TokenUtil
import com.fast.api.util.UserUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * Put common logic between controllers here
 */
@RestController
open class MainController {

    @Autowired
    lateinit var tokenService: TokenService

    @Autowired
    lateinit var usersRepo: UsersRepo

    /**
     * If token is present extract subject and find the user
     * store in our UserUtil singleton
     */
    @ModelAttribute("user")
    fun findUser(request: HttpServletRequest) {

        TokenUtil.extractToken(request)?.let { token ->

            val claims = tokenService.getClaims(token)
            val id = claims.subject

            usersRepo.findByIdOrNull(UUID.fromString(id))?.let {
                UserUtil.user = it
            } ?: kotlin.run {
                println("user not found")
            }
        }
    }
}