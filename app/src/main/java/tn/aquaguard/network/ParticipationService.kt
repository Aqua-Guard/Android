package tn.aquaguard.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import tn.aquaguard.models.Participation

interface ParticipationService {

    @GET("participations/user")
    suspend fun getParticipations(): Response<List<Participation>>

    @POST("participations/participate/{eventId}")
    suspend fun  addParticipation(@Path("eventId") eventId:String): Response<String>

    @GET("participations/participate/{eventId}")
    suspend fun isParticipated(@Path("eventId") eventId: String?): Response<Boolean>

}