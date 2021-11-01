package com.fast.api.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @Expose
    @SerializedName("refresh_token")
    val refreshToken: String,
    @Expose
    @SerializedName("user_id")
    val userId: String
)