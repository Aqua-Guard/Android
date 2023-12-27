package tn.aquaguard.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import tn.aquaguard.models.Discution
import tn.aquaguard.models.DiscutionResponse
import tn.aquaguard.models.SearchRequest
import tn.aquaguard.models.SendMessageRequest


interface DiscutionService {

    @POST("/reclamation/getdiscution/{reclamationId}")
    suspend fun getallmessagesofthisreclamation(@Path ("reclamationId") reclamationId: String): Response<List<Discution>>


    @POST("/discution")
    suspend fun sendmessage(@Body request: SendMessageRequest): Response<Discution>



@DELETE("/discution/deleteonlyforuser/{id}")
suspend fun deleteonlyforuser(@Path("id") id :String):Response<String?>

@PUT("/discution/deleteonlyforuser/{id}")
suspend fun deleteforall(@Path("id") id :String):Response<String?>

}