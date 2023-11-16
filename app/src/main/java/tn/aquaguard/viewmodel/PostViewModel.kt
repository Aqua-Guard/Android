package tn.aquaguard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tn.aquaguard.repository.PostRepository
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tn.aquaguard.models.Post

class PostViewModel : ViewModel() {
    private val repository = PostRepository()

    val posts = liveData {
        val postData = repository.getAllPosts()
        emit(postData ?: emptyList())
    }
    // LiveData for a single post
    private val _singlePost = MutableLiveData<Post?>()
    val singlePost: MutableLiveData<Post?> = _singlePost

    fun fetchPostById(postId: String) {
        viewModelScope.launch {
            try {
                val postDetail = repository.getPostById(postId)
                _singlePost.value = postDetail
            } catch (e: Exception) {
                println("\"Error fetching post")

                _singlePost.value = null // Or handle the error as appropriate
            }
        }
    }
}