package tn.aquaguard.repository

import tn.aquaguard.models.Event
import tn.aquaguard.network.RetrofitClient

class EventRepository {
    suspend fun getAllEvents(): List<Event>? {
        return RetrofitClient.apiService.getAllEvents().body()
    }
}