package tn.aquaguard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import tn.aquaguard.repository.ActualiteRepository

class ActualiteViewModel: ViewModel() {
    private val repository =ActualiteRepository()

    val events = liveData {
        val ActualiteData = repository.getAll()
        emit(ActualiteData ?: emptyList())
    }
}