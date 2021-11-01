package com.fast.api.controller

import com.fast.api.model.User
import com.fast.api.model.request.CreateUserRequest
import com.fast.api.model.request.LoginUserRequest
import com.fast.api.model.request.RefreshTokenRequest
import com.fast.api.security.TokenService
import com.fast.api.service.AuthService
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Handles logging in and registering users
 */
@RestController
@RequestMapping("/api/v1/auth")
class AuthController : MainController() {

    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var authService: AuthService


    @ApiOperation("sign up a user")
    @PostMapping("/signup")
    fun signUp(@RequestBody createUserRequest: CreateUserRequest, request: HttpServletRequest, response: HttpServletResponse): User? {
        return authService.signUp(createUserRequest, response)
    }

    /**
     * login user
     */
    @PostMapping("/login")
    @ApiOperation("logs in a user and response with header jwt info")
    fun login(@RequestBody loginUserRequest: LoginUserRequest, request: HttpServletRequest, response: HttpServletResponse): Any? {
        return authService.login(response, loginUserRequest)
    }

    /**
     * Refresh the auth token
     */
    @PostMapping("/refresh_token")
    @ApiOperation("refreshes thee auth token given the users current token if the refresh token is valid")
    fun refreshToken(@RequestBody request: RefreshTokenRequest, response: HttpServletResponse): Unit? {
        tokenService.refreshToken(request, response)
        return null
    }
}