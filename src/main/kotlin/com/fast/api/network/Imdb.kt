package com.fast.api.network

import com.fast.api.`interface`.ImdbApiInterface
import com.fast.api.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Value
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Imdb {

    @Value("\${imdb.key}")
    private lateinit var apiKeyValue: String

    companion object {
        const val API_ENDPOINT = "http://www.omdbapi.com"
        const val API_KEY = "apikey"
    }

    val imdbApiClient: ImdbApiInterface by lazy {

        println("setting up api client")

        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->

                println("adding interceptors")

                val original = chain.request()
                val builder = original.newBuilder()
                val originalHttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(API_KEY, apiKeyValue)
                    .build()

                builder.header(Constants.ACCEPT, Constants.APPLICATION_JSON)
                builder.header(Constants.ACCEPT_ENCODING, Constants.APPLICATION_JSON)
                builder.header(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                builder.method(original.method, original.body)
                builder.url(url)

                val originalRequest = chain.proceed(builder.build())
                val newRequest = originalRequest.newBuilder()

                newRequest.build()
            }
            .build()

        Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ImdbApiInterface::class.java)
    }
}