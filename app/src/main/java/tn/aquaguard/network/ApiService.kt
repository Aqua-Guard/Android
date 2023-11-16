package tn.aquaguard.network

import retrofit2.Response
import retrofit2.http.GET
import tn.aquaguard.models.Event
import tn.aquaguard.models.Post
import retrofit2.http.Path

interface ApiService {
    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @GET("posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: String): Response<Post>

    @GET("/events")
    suspend fun getAllEvents(): Response<List<Event>>

}
