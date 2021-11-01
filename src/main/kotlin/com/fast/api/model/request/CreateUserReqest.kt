package com.fast.api.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateUserRequest(
    @JsonProperty("email")
    val email: String,
    @JsonProperty("password")
    val password: String
)