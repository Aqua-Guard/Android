package tn.aquaguard.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Avis
import tn.aquaguard.models.SearchRequest

interface ActualiteService {

    @GET("/act")
    suspend fun getAll(): Response<List<Actualites>>
    @POST("/act/search")
    suspend fun searchActualites(@Body request: SearchRequest): Response<List<Actualites>>
    @GET("/avis/{iduser}/{idactualite}")
    suspend fun getAvisByIds(
        @Path("iduser") iduser: String,
        @Path("idactualite") idactualite: String
    ): Response<Avis>
    @POST("/avis/addupdates")
    suspend fun addOrUpdateAvis(@Body request: Avis): Response<Unit>
    @POST("/avis")
    suspend fun addOnceAvis(@Body request: Avis): Response<Unit>
@POST("act/views/{actualiteId}")
suspend fun addview(@Path("actualiteId") actualiteId: String):Response<String?>
}