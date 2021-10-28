package com.fast.api.`interface`

import retrofit2.Response
import retrofit2.http.GET

import retrofit2.http.Query

interface ImdbApiInterface {

    @GET("")
    fun getMovie(@Query("i") i: String): Response<String>
}