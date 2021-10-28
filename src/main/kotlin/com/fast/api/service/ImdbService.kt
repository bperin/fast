package com.fast.api.service

import com.fast.api.`interface`.ImdbApiInterface
import com.fast.api.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.annotation.PostConstruct

/**
 * Class to interface with IMDB api
 */
@Service
class ImdbService {

    @Value("\${imdb.key}")
    private lateinit var apiKeyValue: String

    lateinit var apiClient: ImdbApiInterface

    companion object {
        const val API_ENDPOINT = "http://www.omdbapi.com"
        const val API_KEY = "apikey"
    }

    /**
     * Build client when bean initialized
     */
    @PostConstruct
    fun setup() {

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

        apiClient = Retrofit.Builder()
            .baseUrl(API_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ImdbApiInterface::class.java)
    }
}