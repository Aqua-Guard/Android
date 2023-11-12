package tn.aquaguard.viewmodel

import androidx.lifecycle.ViewModel
import tn.aquaguard.repository.PostRepository
import androidx.lifecycle.liveData

class PostViewModel : ViewModel() {
    private val repository = PostRepository()

    val posts = liveData {
        val postData = repository.getAllPosts()
        emit(postData ?: emptyList())
    }
}