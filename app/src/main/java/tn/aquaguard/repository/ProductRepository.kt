package tn.aquaguard.repository

import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Product
import tn.aquaguard.network.RetrofitClient

class ProductRepository {

    suspend fun getAll(): List<Product>? {
        return RetrofitClient.StoreService.getAll().body()
    }


}