package com.example.rickandmortyepisodeguide.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmortyepisodeguide.data.repository.RnMRepository

class MainViewModelFactory(private val repository:RnMRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}