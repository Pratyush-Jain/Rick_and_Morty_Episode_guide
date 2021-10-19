package com.example.rickandmortyepisodeguide.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    val episodeInstance:RnMService

    init {
        val retrofit =  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        episodeInstance = retrofit.create(RnMService::class.java)
    }
}