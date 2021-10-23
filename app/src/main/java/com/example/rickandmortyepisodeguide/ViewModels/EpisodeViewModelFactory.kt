package com.example.rickandmortyepisodeguide.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyepisodeguide.data.repository.RnMRepository


class EpisodeViewModelFactory(private val repository: RnMRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EpisodeViewModel(repository) as T
    }
}