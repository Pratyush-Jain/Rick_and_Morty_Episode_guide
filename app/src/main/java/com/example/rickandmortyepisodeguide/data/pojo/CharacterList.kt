package com.example.rickandmortyepisodeguide.data.pojo

import com.google.gson.annotations.SerializedName

data class CharacterList(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val results: List<CharacterInfo>
    )
