package tn.aquaguard.repository

import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Avis
import tn.aquaguard.network.RetrofitClient

class ActualiteRepository {
    suspend fun getAll(): List<Actualites>? {
        return RetrofitClient.actualiteService.getAll().body()
    }
    suspend fun getAvisByIds(iduser: String, idactualite: String): Avis? {
        return RetrofitClient.actualiteService.getAvisByIds(iduser, idactualite).body()
    }

    suspend fun addAvis(idUser: String, idActualites: String, aviss: Any): Unit? {
        var avis =Avis(idUser,idActualites,aviss)
        return RetrofitClient.actualiteService.addOnceAvis(avis).body()
    }

    suspend fun addOrUpdateAvis(avis: Avis): Unit? {
        return RetrofitClient.actualiteService.addOrUpdateAvis(avis).body()
    }
}