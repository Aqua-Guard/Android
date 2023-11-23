package tn.aquaguard.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import tn.aquaguard.models.Actualites
import tn.aquaguard.models.Post
import retrofit2.http.Path
import tn.aquaguard.models.AddEventRequest
import tn.aquaguard.models.Comment
import tn.aquaguard.models.CommentRequest


interface PostService {
    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @GET("/posts/postByCurrentUser")
    suspend fun getAllPostsByCurentUser(): Response<List<Post>>

    @GET("posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: String): Response<Post>

    @POST("/posts/like/{postId}")
    suspend fun addLike(@Path("postId") postId: String): Response<String>

    @Multipart
    @POST("/posts")
    suspend fun addPost(
        @Part("description") description: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<String>

    @Multipart
    @PUT("/posts/{postId}")
    suspend fun updatePost(
        @Path("postId") postId: String,
        @Part("description") description: RequestBody,
      //  @Part image: MultipartBody.Part
    ): Response<String>

    @POST("/posts/dislike/{postId}")
    suspend fun dislikePost(@Path("postId") postId: String): Response<String>

    @GET("/posts/isLiked/{postId}")
    suspend fun isPostLiked(@Path("postId") postId: String): Response<Boolean>

    @POST("/posts/{postId}/comments")
    suspend fun addComment(
        @Path("postId") postId: String,
        @Body request: CommentRequest
    ): Response<Unit>

    @PUT("/comments/{commentId}")
    suspend fun updateComment(
        @Path("commentId") commentId: String,
        @Body request: CommentRequest
    ): Response<Unit>

    @DELETE("/comments/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: String): Response<Unit>

    @DELETE("/posts/{postId}")
    suspend fun deletePost(@Path("postId") postId: String): Response<Unit>


    @GET("posts/{postId}/comments")
    suspend fun getCommentByIdPost(@Path("postId") postId: String): Response<List<Comment>>


}
