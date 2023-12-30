package tn.aquaguard.repository

import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Avis
import tn.aquaguard.models.SearchRequest
import tn.aquaguard.network.RetrofitClient

class ActualiteRepository {
    suspend fun getAll(): List<Actualites>? {
        return RetrofitClient.actualiteService.getAll().body()
    }

    suspend fun getAvisByIds(iduser: String, idactualite: String): Avis? {
        return RetrofitClient.actualiteService.getAvisByIds(iduser, idactualite).body()
    }
    suspend fun searchActualites(about: SearchRequest): List<Actualites>? {
        return RetrofitClient.actualiteService.searchActualites(about).body()
    }


    suspend fun addOrUpdateAvis(avis: Avis): Unit? {
        return RetrofitClient.actualiteService.addOrUpdateAvis(avis).body()
    }
    suspend fun addview(actualiteId :String):String?{
        return RetrofitClient.actualiteService.addview(actualiteId).body()
    }
}