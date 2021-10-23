package com.example.rickandmortyepisodeguide.ViewModels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeInfo
import com.example.rickandmortyepisodeguide.data.repository.RnMRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository:RnMRepository): ViewModel() {

    val totalEpisodes = 41
    val PAGING_COUNT = 10

    var allEpisodesLivedata = MutableLiveData<List<EpisodeInfo>>()
    var allEpisodes = mutableListOf<EpisodeInfo>()
    var episodeBySearch = MutableLiveData<List<EpisodeInfo>>()


    fun getEpisodeBySearch(p0: String) {
        viewModelScope.launch(Dispatchers.IO) {
           val result = repository.getEpisodeBySearch(p0)
            if(result.isSuccessful){
                val data = result.body()
                episodeBySearch.postValue(data!!.results)
            }

        }
    }

    fun fetchData(startEpisode: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val range = (startEpisode..startEpisode + PAGING_COUNT).toList()
            val fetchedEpisodesResult = repository.fetchEpisodes(range)
            // To log whole response
//            val len = fetchedEpisodesResult.body().toString().length
//            val siz = 2000
//            val top = len/siz
//
//            for(i in 0..top){
//                var start = i*siz
//                var end = (i+1)*siz
//                if (end>len){
//                    end = len
//                }
//                Log.d("RESSULT",fetchedEpisodesResult.body().toString().substring(start,end))
//            }
//            Log.d("RResult",fetchedEpisodesResult.body().toString())
            if(fetchedEpisodesResult.isSuccessful) {
                allEpisodesLivedata.postValue(fetchedEpisodesResult.body())
            }
        }


    }


}