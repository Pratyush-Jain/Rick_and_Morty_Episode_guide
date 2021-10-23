package com.example.rickandmortyepisodeguide.ViewModels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyepisodeguide.data.pojo.CharacterInfo
import com.example.rickandmortyepisodeguide.data.pojo.CharacterList
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeInfo
import com.example.rickandmortyepisodeguide.data.pojo.Info
import com.example.rickandmortyepisodeguide.data.repository.RnMRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class EpisodeViewModel(private val repository: RnMRepository): ViewModel() {
    var CharacterLiveData = MutableLiveData<CharacterList>()
    var episodeData  = MutableLiveData<EpisodeInfo>()
    val genders = listOf("Female","Male","Genderless","Unknown")
    val charactersOfEpisode = mutableListOf<String>()
    val characterUrlLiveData = MutableLiveData<List<String>>()
    var filteredCharacter = mutableListOf<CharacterInfo>()



    fun fetchEpisodeData(episodeId: Int) {
        viewModelScope.launch(Dispatchers.IO){
            var result = repository.getSingleEpisode(episodeId)
            if(result.isSuccessful) {
                episodeData.postValue(result.body())
            }

        }

    }

    fun filterCharacterByGender(gender: String,episodeId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            for(url in charactersOfEpisode){
                val id = url.split("/")
                val result = repository.getCharacter(id[id.size-1].toInt())
                if (result.isSuccessful){
                    filteredCharacter.add(result.body()!!)
                }
            }
            var genderedcharacter = filteredCharacter.filter{
                it.gender == gender
            }

            CharacterLiveData.postValue(CharacterList(Info(0,"",0,""),genderedcharacter))
            filteredCharacter.clear()


        }

    }
    fun filterCharacterByNameAndGender(gender: String,name:String,episodeId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            filteredCharacter.clear()
            var result = repository.filterCharacterByNameAndGender(gender, name,episodeId)
            if (result.isSuccessful) {
                var info = result.body()!!
                for( character in info.results)
                    if( character.url in charactersOfEpisode){
                        filteredCharacter.add(character)
                    }
            }
            CharacterLiveData.postValue(CharacterList(Info(0,"",0,""),filteredCharacter))

        }
    }

    fun filterCharacters(selectedItem: String, text: String,episodeId:Int) {
        if(text.isEmpty()){
            filteredCharacter.clear()
            filterCharacterByGender(selectedItem,episodeId)
        }else{
            filteredCharacter.clear()
            filterCharacterByNameAndGender(selectedItem,text,episodeId)
        }

    }
    fun getCharacters(){
        viewModelScope.launch(Dispatchers.IO) {
            var response = repository.getCharacter(1).body()!!
            var response2 = repository.getCharacter(2).body()!!
            CharacterLiveData.postValue(CharacterList(Info(0,"",0,""), listOf(response,response2)))

        }
    }

}