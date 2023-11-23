package tn.aquaguard.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tn.aquaguard.models.Actualites
import tn.aquaguard.models.AddEventRequest
import tn.aquaguard.models.Avis

interface ActualiteService {

    @GET("/act")
    suspend fun getAll(): Response<List<Actualites>>
    @POST("/avis")
    suspend fun addOnceAvis(@Body request: Avis): Response<Unit>
}