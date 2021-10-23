package com.example.rickandmortyepisodeguide.data.repository


import com.example.rickandmortyepisodeguide.data.api.RnMService
import com.example.rickandmortyepisodeguide.data.pojo.CharacterInfo
import com.example.rickandmortyepisodeguide.data.pojo.CharacterList
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeInfo
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeList

import retrofit2.Response

class RnMRepository(private val rnmService:RnMService) {


   suspend fun getSingleEpisode(id: Int): Response<EpisodeInfo> {
        return rnmService.getSingleEpisode(id)
    }


   suspend fun getEpisodeBySearch(p0: String):  Response<EpisodeList> {
       return rnmService.getEpisodeBySearch(p0)
    }


    suspend fun fetchEpisodes(range: List<Int>):Response<List<EpisodeInfo>> {
        return rnmService.fetchEpisodes(range)

    }
    suspend fun filterCharacterByNameAndGender(gender: String,name:String):Response<CharacterList> {
        return rnmService.filterCharacterByNameAndGender(gender,name)
    }
    suspend fun getCharacter(id:Int):Response<CharacterInfo>{
        return rnmService.getCharacter(id)
    }
}