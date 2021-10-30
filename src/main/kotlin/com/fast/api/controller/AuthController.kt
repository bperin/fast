package com.fast.api.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Handles logging in and registering users
 */
@RestController
@RequestMapping("/api/v1/auth")
class AuthController : MainController() {

}