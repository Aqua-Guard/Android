//package
package tn.aquaguard.network


//imports
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import tn.aquaguard.models.Reclamation


interface ReclamationService {


        @GET("/reclamation/getbyuserId")
        suspend fun getReclamationsByUserId(): Response<List<Reclamation>>
        @Multipart
        @POST("/reclamation")
        suspend fun addReclamation(
                @Part("title") title: RequestBody,
                @Part("desc") description: RequestBody,
                @Part image: MultipartBody.Part
        ): Response<Reclamation>
}
