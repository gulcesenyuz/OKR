package com.example.okrprojectfinal.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.okrprojectfinal.repository.MovieRepository
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(private val repository: MovieRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}