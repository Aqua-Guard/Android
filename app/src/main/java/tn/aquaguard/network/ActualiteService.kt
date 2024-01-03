package tn.aquaguard.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Avis
import tn.aquaguard.models.SearchRequest
import tn.aquaguard.models.Viewss

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
    @GET("/act/getliketable/{actualiteId}")
    suspend fun getlikeandcom(@Path("actualiteId") actualiteId: String): Response<Viewss>
    @POST("/avis/addupdates")
    suspend fun addOrUpdateAvis(@Body request: Avis): Response<Unit>
    @POST("/avis")
    suspend fun addOnceAvis(@Body request: Avis): Response<Unit>
@POST("act/views/{actualiteId}")
suspend fun addview(@Path("actualiteId") actualiteId: String):Response<String?>

    @PUT("act/addlikeforactualite/{actualiteId}/{like}")
    suspend fun addlike(
        @Path ("like") like:Int,
        @Path("actualiteId") actualiteId: String):Response<String?>

    @PUT("act/addreviw/{actualiteId}/{review}")
    suspend fun addcomment(
        @Path ("actualiteId") actualiteId:String,
        @Path("review") review: String):Response<String?>



}

