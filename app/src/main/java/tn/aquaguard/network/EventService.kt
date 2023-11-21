package tn.aquaguard.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tn.aquaguard.models.AddEventRequest
import tn.aquaguard.models.Event

interface EventService {
    @GET("/events")
    suspend fun getAllEvents(): Response<List<Event>>

    @POST("/events")
    suspend fun addEvent(@Body request: AddEventRequest): Response<String?>

    @GET("/events/eventByCurrentUser")
    suspend fun getAllEventsByCurrentUser(): Response<List<Event>>

}