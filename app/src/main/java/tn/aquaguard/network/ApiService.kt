package tn.aquaguard.network

import retrofit2.Response
import retrofit2.http.GET
import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Post
import retrofit2.http.Path


interface ApiService {
    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>


    @GET("posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: String): Response<Post>


    @GET("/act")
    suspend fun getAll(): Response<List<Actualites>>

}
