package tn.aquaguard.repository

import tn.aquaguard.models.Actualites
import tn.aquaguard.network.RetrofitClient

class ActualiteRepository {
    suspend fun getAll(): List<Actualites>? {
        return RetrofitClient.apiService.getAll().body()
    }
}