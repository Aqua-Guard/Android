package tn.aquaguard.repository

import tn.aquaguard.models.Comment
import tn.aquaguard.models.CommentRequest
import tn.aquaguard.models.Post
import tn.aquaguard.network.RetrofitClient


class PostRepository {
    suspend fun getAllPosts(): List<Post>? {
        return RetrofitClient.postService.getAllPosts().body()
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

    suspend fun isPostLiked(postId: String): String? {
        return RetrofitClient.postService.isPostLiked(postId).body()
    }

    suspend fun addComment(postId: String, commentText: String): String? {
        val commentRequest = CommentRequest(commentText)
        return RetrofitClient.postService.addComment(postId, commentRequest).body()
    }
    suspend fun deleteComment(commentId: String): Unit? {
        return RetrofitClient.postService.deleteComment(commentId).body()
    }



}