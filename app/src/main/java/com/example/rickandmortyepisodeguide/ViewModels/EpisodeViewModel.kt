package com.example.rickandmortyepisodeguide.ViewModels


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


class EpisodeViewModel(private val repository: RnMRepository): ViewModel() {
    var characterLiveData = MutableLiveData<CharacterList>()
    var episodeData  = MutableLiveData<EpisodeInfo>()
    val genders = listOf("Female","Male","Genderless","Unknown")
    val charactersOfEpisode = mutableListOf<String>()
    val characterUrlLiveData = MutableLiveData<List<String>>()
    var filteredCharacter = mutableListOf<CharacterInfo>()



    fun fetchEpisodeData(episodeId: Int) {
        viewModelScope.launch(Dispatchers.IO){
            val result = repository.getSingleEpisode(episodeId)
            if(result.isSuccessful) {
                episodeData.postValue(result.body())
            }

        }

    }

    fun filterCharacterByGender(gender: String) {
        viewModelScope.launch(Dispatchers.IO) {
            for(url in charactersOfEpisode){
                val id = url.split("/")
                val result = repository.getCharacter(id[id.size-1].toInt())
                if (result.isSuccessful){
                    filteredCharacter.add(result.body()!!)
                }
            }
            val genderFilteredCharacters = filteredCharacter.filter{
                it.gender == gender
            }

            characterLiveData.postValue(CharacterList(Info(0,"",0,""),genderFilteredCharacters))
            filteredCharacter.clear()


        }

    }
    fun filterCharacterByNameAndGender(gender: String,name:String) {
        viewModelScope.launch(Dispatchers.IO) {
            filteredCharacter.clear()
            val result = repository.filterCharacterByNameAndGender(gender, name)
            if (result.isSuccessful) {
                val info = result.body()!!
                for( character in info.results)
                    if( character.url in charactersOfEpisode){
                        filteredCharacter.add(character)
                    }
            }
            characterLiveData.postValue(CharacterList(Info(0,"",0,""),filteredCharacter))

        }
    }

    fun filterCharacters(selectedItem: String, text: String) {
        if(text.isEmpty()){
            filteredCharacter.clear()
            filterCharacterByGender(selectedItem)
        }else{
            filteredCharacter.clear()
            filterCharacterByNameAndGender(selectedItem,text)
        }

    }

}