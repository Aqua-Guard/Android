package tn.aquaguard.repository
import tn.aquaguard.models.Reclamation
import tn.aquaguard.network.RetrofitClient




class ReclamationRepository {


    suspend fun getAllreclamation(): List<Reclamation>? {
        return RetrofitClient.reclamationservice.getAllreclamation().body()
    }
}