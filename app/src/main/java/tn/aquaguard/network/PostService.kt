package tn.aquaguard.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Post
import retrofit2.http.Path
import tn.aquaguard.models.AddEventRequest
import tn.aquaguard.models.Comment
import tn.aquaguard.models.CommentRequest


interface PostService {
    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>


    @GET("posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: String): Response<Post>

    @POST("/posts/like/{postId}")
    suspend fun addLike(@Path("postId") postId: String): Response<String>

    @POST("/posts/dislike/{postId}")
    suspend fun dislikePost(@Path("postId") postId: String): Response<String>

    @GET("/posts/isLiked/{postId}")
    suspend fun isPostLiked(@Path("postId") postId: String): Response<String>

    @POST("/posts/{postId}/comments")
    suspend fun addComment(@Path("postId") postId: String, @Body request: CommentRequest): Response<String?>

    @DELETE("/comments/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: String): Response<Unit>
}
