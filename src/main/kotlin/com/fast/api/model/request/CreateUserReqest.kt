package com.fast.api.model.request

data class CreateUserRequest(
    val email: String,
    val password: String
)