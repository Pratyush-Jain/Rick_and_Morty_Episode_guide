package com.example.rickandmortyepisodeguide.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyepisodeguide.data.pojo.CharacterList
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeInfo
import com.example.rickandmortyepisodeguide.data.repository.RnMRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class EpisodeViewModel(private val repository: RnMRepository): ViewModel() {
    var CharacterLiveData = MutableLiveData<CharacterList>()
    var episodeData  = MutableLiveData<EpisodeInfo>()
    val genders = listOf("Female","Male","Genderless","Unknown")
    fun fetchEpisodeData(episodeId: Int) {
        viewModelScope.launch(Dispatchers.IO){
            var result = repository.getSingleEpisode(episodeId)
            if(result.isSuccessful) {
                episodeData.postValue(result.body())
            }
        }

    }

    fun filterCharacterByGender(gender: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var result = repository.filterCharacterByGender(gender)
            if(result.isSuccessful) {
                CharacterLiveData.postValue(result.body())
            }

        }
    }
    fun filterCharacterByNameAndGender(gender: String,name:String) {
        viewModelScope.launch(Dispatchers.IO) {
            var result = repository.filterCharacterByNameAndGender(gender, name)
            if (result.isSuccessful) {
                CharacterLiveData.postValue(result.body())
            }

        }
    }

}