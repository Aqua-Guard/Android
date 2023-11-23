package tn.aquaguard.repository

import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Avis
import tn.aquaguard.models.CommentRequest
import tn.aquaguard.network.RetrofitClient

class ActualiteRepository {
    suspend fun getAll(): List<Actualites>? {
        return RetrofitClient.actualiteService.getAll().body()
    }
    suspend fun addAvis(avis: Avis): Unit? {
        val avisrequist = Avis(avis.Iduser,avis.actualiteTitle,avis.avis)
        return RetrofitClient.actualiteService.addOnceAvis(avisrequist).body()
    }
}