package com.example.rickandmortyepisodeguide.data.api

import com.example.rickandmortyepisodeguide.data.pojo.CharacterInfo
import com.example.rickandmortyepisodeguide.data.pojo.CharacterList
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeInfo
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeList
import retrofit2.Call

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RnMService {

    @GET("episode/{id}")
   suspend fun getSingleEpisode(@Path("id")id:Int):Response<EpisodeInfo>

    @GET("episode")
    suspend fun getAllEpisodes():Response<EpisodeList>

    @GET("episode")
    suspend fun getEpisodeBySearch(@Query("name")p0: String): Response<EpisodeList>

//    @GET("episode/{ids}")
//    suspend fun getEpisodeByList(@Path("ids")episodeList: List<Int>): Response<List<EpisodeInfo>>

    @GET("episode/{ids}")
    suspend fun fetchEpisodes(@Path("ids")range: List<Int>): Response<List<EpisodeInfo>>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id")id:Int):Response<CharacterInfo>

    @GET("character")
    suspend fun filterCharacterByGender(@Query("gender")gender: String):Response<CharacterList>

    @GET("character")
    suspend fun filterCharacterByNameAndGender(@Query("gender")gender: String,@Query("name")name: String):Response<CharacterList>


//    @GET()
//    fun getEpisodesBySeason(season: Int): List<EpisodeInfo>
}