package tn.aquaguard.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import tn.aquaguard.models.Comment
import tn.aquaguard.models.CommentRequest
import tn.aquaguard.models.Post
import tn.aquaguard.network.RetrofitClient


class PostRepository {
    suspend fun getAllPosts(): List<Post>? {
        return RetrofitClient.postService.getAllPosts().body()
    }

    // Method to add a new post
    suspend fun addPost(description: RequestBody, image: MultipartBody.Part): Response<String> {
        return RetrofitClient.postService.addPost(description, image)
    }

    suspend fun updatePost(description: RequestBody, image: MultipartBody.Part): Response<String> {
        return RetrofitClient.postService.addPost(description, image)
    }

    suspend fun getAllPostsByCurentUser(): List<Post>? {
        return RetrofitClient.postService.getAllPostsByCurentUser().body()
    }

    suspend fun getPostById(postId: String): Post? {
        return RetrofitClient.postService.getPostById(postId).body()
    }

    suspend fun addLike(postId: String): String? {
        return RetrofitClient.postService.addLike(postId).body()
    }

    suspend fun dislikePost(postId: String): String? {
        return RetrofitClient.postService.dislikePost(postId).body()
    }

    suspend fun isPostLiked(postId: String): Boolean? {
        return RetrofitClient.postService.isPostLiked(postId).body()
    }

    suspend fun addComment(postId: String,commentText: String): Unit? {
        val commentRequest = CommentRequest(commentText)
        return RetrofitClient.postService.addComment(postId,commentRequest).body()
    }

    suspend fun updateComment(commentId: String, commentText: String): Response<Unit> {
        val commentRequest = CommentRequest(commentText)
        return RetrofitClient.postService.updateComment(commentId, commentRequest)
    }
    suspend fun deleteComment(commentId: String): Unit? {
        return RetrofitClient.postService.deleteComment(commentId).body()
    }

    suspend fun deletePost(postId: String): Unit? {
        return RetrofitClient.postService.deletePost(postId).body()
    }

    suspend fun getCommentsByIdPost(postId: String): List<Comment>? {
        return RetrofitClient.postService.getCommentByIdPost(postId).body()
    }



}