package com.fast.api.service

import com.fast.api.model.User
import com.fast.api.model.request.CreateUserRequest
import com.fast.api.model.request.LoginUserRequest
import com.fast.api.repo.UsersRepo
import com.fast.api.security.BcryptUtil
import com.fast.api.security.TokenService
import com.fast.api.util.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.servlet.http.HttpServletResponse

@Service
class AuthService {

    @Value("\${primary.owner.email}")
    lateinit var primaryOwnerEmail: String

    @Autowired
    lateinit var usersRepo: UsersRepo

    @Autowired
    lateinit var tokenService: TokenService

    /**
     * Logs in a user if the password hash is equal
     */
    @Throws(ResponseStatusException::class)
    fun login(httpServletResponse: HttpServletResponse, loginUserRequest: LoginUserRequest): User? {

        usersRepo.findUserByEmail(loginUserRequest.email)?.let { user ->

            if (BcryptUtil.checkPassword(loginUserRequest.password, user.password)) {
                tokenService.login(user, httpServletResponse)
                return user
            }
        } ?: kotlin.run {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid email or password")
        }
        return null
    }

    /**
     * Creates a new user from the request
     * Use bcrypt to hash the password
     * If the user is our primary owner set from env set them as owner
     */
    @Transactional
    fun signUp(createUserRequest: CreateUserRequest, httpServletResponse: HttpServletResponse): User? {

        createUserRequest.email?.let {
            usersRepo.findUserByEmail(it)?.let {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, Constants.EMAIL + " " + Constants.ALREADY_IN_USE)
            }
        } ?: run {

            if (createUserRequest.password.isNullOrEmpty() || createUserRequest.password.length < 7) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "password too short")
            }
            val user = User()
            user.email = createUserRequest.email
            user.password = BcryptUtil.hashPassword(createUserRequest.password)

            if (createUserRequest.email.lowercase(Locale.getDefault()) == primaryOwnerEmail) {
                user.owner = true
            }
            usersRepo.save(user)
            return user
        }
    }
}
