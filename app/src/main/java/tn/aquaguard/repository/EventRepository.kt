package tn.aquaguard.repository


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
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

    suspend fun deleteEvent(eventId: String):String? {
        return RetrofitClient.eventService.deleteEvent(eventId).body()
    }


    suspend fun updateEvent(
        eventId: String,
        imageUri:MultipartBody.Part,
        name: RequestBody,
        description: RequestBody,
        location: RequestBody,
        startDate: RequestBody,
        endDate: RequestBody
    ): Response<String?> {


        return RetrofitClient.eventService.updateEvent(eventId, imageUri, name, description, location, startDate, endDate)
    }


}