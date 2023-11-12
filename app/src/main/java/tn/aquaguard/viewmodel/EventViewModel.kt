package tn.aquaguard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import tn.aquaguard.repository.EventRepository

class EventViewModel: ViewModel() {
    private val repository = EventRepository()

    val events = liveData {
        val eventData = repository.getAllEvents()
        emit(eventData ?: emptyList())
    }
}