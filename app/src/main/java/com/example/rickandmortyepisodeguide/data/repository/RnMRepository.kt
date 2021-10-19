package com.example.rickandmortyepisodeguide.data.repository


import com.example.rickandmortyepisodeguide.data.api.RnMService
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeInfo
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeList

import retrofit2.Response

class RnMRepository(private val rnmService:RnMService) {


//    fun getSingleEpisode(id: Int): Response<EpisodeInfo> {
//        return rnmService.getSingleEpisode(id)
//    }
//
//    fun getAllEpisodes(): Response<EpisodeList> {
//        return rnmService.getAllEpisodes()
//    }
}