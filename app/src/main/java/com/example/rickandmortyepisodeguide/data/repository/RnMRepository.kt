package com.example.rickandmortyepisodeguide.data.repository


import com.example.rickandmortyepisodeguide.data.api.RnMService
import com.example.rickandmortyepisodeguide.data.pojo.CharacterList
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeInfo
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeList
import retrofit2.Call

import retrofit2.Response

class RnMRepository(private val rnmService:RnMService) {


   suspend fun getSingleEpisode(id: Int): Response<EpisodeInfo> {
        return rnmService.getSingleEpisode(id)
    }

   suspend fun getAllEpisodes(): Response<EpisodeList> {
        return rnmService.getAllEpisodes()
    }

   suspend fun getEpisodeBySearch(p0: CharSequence):  Response<EpisodeList> {
       return rnmService.getEpisodeBySearch(p0)
    }

//    suspend fun getEpisodeByList(episodeList: List<Int>): Response<List<EpisodeInfo>> {
//        return rnmService.getEpisodeByList(episodeList)
//    }

    suspend fun fetchEpisodes(range: List<Int>):Response<List<EpisodeInfo>> {
        return rnmService.fetchEpisodes(range)

    }

    suspend fun filterCharacterByGender(gender: String):Response<CharacterList> {
        return rnmService.filterCharacterByGender(gender)
    }

    suspend fun filterCharacterByNameAndGender(gender: String,name:String):Response<CharacterList> {
        return rnmService.filterCharacterByNameAndGender(gender,name)


    }
}