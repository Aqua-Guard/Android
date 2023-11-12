package tn.aquaguard.network


import retrofit2.Response
import retrofit2.http.GET
import tn.aquaguard.models.Post

interface ApiService {
    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>
}