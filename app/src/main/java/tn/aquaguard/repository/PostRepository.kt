package tn.aquaguard.repository

import tn.aquaguard.models.Post
import tn.aquaguard.network.RetrofitClient


class PostRepository {
    suspend fun getAllPosts(): List<Post>? {
        return RetrofitClient.apiService.getAllPosts().body()
    }
}