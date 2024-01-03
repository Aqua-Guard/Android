package tn.aquaguard.repository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import tn.aquaguard.models.Reclamation
import tn.aquaguard.network.RetrofitClient




class ReclamationRepository {


    suspend fun getAllreclamation(): List<Reclamation>? {
        return RetrofitClient.reclamationservice.getReclamationsByUserId().body()
    }

    suspend fun getReclamationsByUserId(): List<Reclamation>? {
        return RetrofitClient.reclamationservice.getReclamationsByUserId().body()
    }

    suspend fun addReclamation(title: RequestBody, description: RequestBody, image: MultipartBody.Part): Reclamation? {
        return RetrofitClient.reclamationservice.addReclamation(title,description,image).body()
    }
}