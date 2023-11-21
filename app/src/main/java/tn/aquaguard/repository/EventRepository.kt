package tn.aquaguard.repository

import tn.aquaguard.models.Event
import tn.aquaguard.network.RetrofitClient

class EventRepository {
    suspend fun getAllEvents(): List<Event>? {
        return RetrofitClient.eventService.getAllEvents().body()
    }
   /* suspend fun addEvent(event: Event){
        return RetrofitClient.eventService.addEvent(event).body()
    }*/

    suspend fun getAllEventsByCurrentUser(): List<Event>? {
        return RetrofitClient.eventService.getAllEventsByCurrentUser().body()
    }
}