package tn.aquaguard.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import tn.aquaguard.models.Event

interface EventService {
    @GET("/events")
    suspend fun getAllEvents(): Response<List<Event>>

    @Multipart
    @POST("/events")
    suspend fun addEvent(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("Description") description: RequestBody,
        @Part("lieu") location: RequestBody,
        @Part("DateDebut") startDate: RequestBody,
        @Part("DateFin") endDate: RequestBody
    ): Response<String>

    @GET("/events/eventByCurrentUser")
    suspend fun getAllEventsByCurrentUser(): Response<List<Event>>

    @DELETE("/events/{eventId}")
    suspend fun deleteEvent(@Path("eventId") postId: String): Response<String?>

    @Multipart
    @PUT("/events/{id}")
    suspend fun updateEvent(
        @Path("id") eventId: String,
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("location") location: RequestBody,
        @Part("startDate") startDate: RequestBody,
        @Part("endDate") endDate: RequestBody
    ): Response<String?>


}