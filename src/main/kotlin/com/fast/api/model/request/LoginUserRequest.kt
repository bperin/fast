package com.fast.api.model.request

data class LoginUserRequest(
    val email: String,
    val password: String,
)