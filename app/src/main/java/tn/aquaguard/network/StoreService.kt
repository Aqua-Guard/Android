package tn.aquaguard.network

import retrofit2.Response
import retrofit2.http.GET
import tn.aquaguard.models.Product

interface StoreService {

    @GET("/produit")
    suspend fun getAll(): Response<List<Product>>

}