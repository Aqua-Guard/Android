package tn.aquaguard.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import tn.aquaguard.models.AddEventRequest
import tn.aquaguard.models.Event

interface EventService {
    @GET("/events")
    suspend fun getAllEvents(): Response<List<Event>>

    @POST("/events")
    suspend fun addEvent(@Body request: AddEventRequest): Response<String?>

    @GET("/events/eventByCurrentUser")
    suspend fun getAllEventsByCurrentUser(): Response<List<Event>>

    @DELETE("/events/{eventId}")
    suspend fun deleteEvent(@Path("eventId") postId: String): Response<String?>


}