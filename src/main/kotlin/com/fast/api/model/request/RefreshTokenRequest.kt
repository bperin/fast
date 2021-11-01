package com.fast.api.model.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RefreshTokenRequest(

    @JsonProperty("refresh_token")
    val refreshToken: String,

    @JsonProperty("user_id")
    val userId: String
)