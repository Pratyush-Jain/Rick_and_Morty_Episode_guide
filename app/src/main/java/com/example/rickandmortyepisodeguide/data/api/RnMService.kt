package com.example.rickandmortyepisodeguide.data.api

import com.example.rickandmortyepisodeguide.data.pojo.EpisodeInfo
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeList
import retrofit2.Call

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RnMService {

    @GET("episode/{id}")
    fun getSingleEpisode(@Path("id")id:Int):Response<EpisodeInfo>

    @GET("episode")
    fun getAllEpisodes():Call<EpisodeList>
}