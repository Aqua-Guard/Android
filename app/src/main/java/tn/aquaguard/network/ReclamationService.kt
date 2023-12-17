//package
package tn.aquaguard.network


//imports
import retrofit2.Response
import retrofit2.http.GET
import tn.aquaguard.models.Reclamation


interface ReclamationService {

        @GET("/reclamation/get")
        suspend fun getAllreclamation(): Response<List<Reclamation>>


}
