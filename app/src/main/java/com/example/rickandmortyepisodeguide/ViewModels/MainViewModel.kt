package com.example.rickandmortyepisodeguide.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeInfo
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeList
import com.example.rickandmortyepisodeguide.data.repository.RnMRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository:RnMRepository): ViewModel() {


//   fun getSingleEpisode(id:Int): Response<EpisodeInfo> {
//           return repository.getSingleEpisode(id)
//
//    }
//
//    fun getAllEpisodes():Response<List<EpisodeList>>{
//        return repository.getAllEpisodes()
//    }

    //val episodeInfo:LiveData<EpisodeInfo> = repository.episodeInfo
}