package tn.aquaguard.repository

import tn.aquaguard.models.Post
import tn.aquaguard.network.RetrofitClient


class PostRepository {
    suspend fun getAllPosts(): List<Post>? {
        return RetrofitClient.apiService.getAllPosts().body()
    }
    suspend fun getPostById(postId: String): Post? {
        return try {
            val response =  RetrofitClient.apiService.getPostById(postId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}