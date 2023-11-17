package tn.aquaguard.network

import retrofit2.Response
import retrofit2.http.GET
import tn.aquaguard.models.Event
import tn.aquaguard.models.Post
import tn.aquaguard.models.Product

interface ApiService {
    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @GET("/events")
    suspend fun getAllEvents(): Response<List<Event>>
    @GET("/produit")
    suspend fun getAllProducts(): Response<List<Product>>
}
