package com.example.pancingapps.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pancingapps.model.Pancing
import com.example.pancingapps.repository.PancingRepository
import kotlinx.coroutines.launch

class PancingViewModel(private val repository: PancingRepository): ViewModel() {
    val allPancings =repository.allPancings.asLiveData()

    fun insert(pancing: Pancing) = viewModelScope.launch{
        repository.insertPancing(pancing)
    }
    fun delete(pancing: Pancing) =viewModelScope.launch {
        repository.DeletePancing(pancing)
    }
    fun update(pancing: Pancing) =viewModelScope.launch {
        repository.updatePancing(pancing)
    }
}

class pancingViewModelFactory(private val repository: PancingRepository): ViewModelProvider.Factory {
    override fun <T :ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((PancingViewModel::class.java))) {
          return PancingViewModel(repository) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}