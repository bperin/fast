package com.fast.api.model.request

import com.google.gson.annotations.Expose

data class LoginUserRequest(
    @Expose
    val email: String,
    @Expose
    val password: String,
)