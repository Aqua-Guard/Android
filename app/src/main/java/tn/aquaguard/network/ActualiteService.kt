package tn.aquaguard.network

import retrofit2.Response
import retrofit2.http.GET
import tn.aquaguard.models.Actualites

interface ActualiteService {

    @GET("/act")
    suspend fun getAll(): Response<List<Actualites>>
}