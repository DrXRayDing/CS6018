package com.example.jokes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Data model for Chuck Norris joke
data class JokeResponse(val value: String)

// API interface
interface ChuckNorrisApi {
    @GET("jokes/random")
    suspend fun getRandomJoke(): JokeResponse

    companion object {
        private const val BASE_URL = "https://api.chucknorris.io/"

        fun create(): ChuckNorrisApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ChuckNorrisApi::class.java)
        }
    }
}