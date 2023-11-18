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
    // LiveData for a single post
    private val _singlePost = MutableLiveData<Post?>()
    val singlePost: MutableLiveData<Post?> = _singlePost

    private val _isPostLiked = MutableLiveData<Boolean>()
    val isPostLiked: LiveData<Boolean> = _isPostLiked

    private val _likeStatus = MutableLiveData<String?>()
    val likeStatus: LiveData<String?> = _likeStatus

    private val _deleteCommentResult = MutableLiveData<Boolean>()
    val deleteCommentResult: LiveData<Boolean> = _deleteCommentResult

    val posts = liveData {
        val postData = repository.getAllPosts()
        emit(postData ?: emptyList())
    }



    fun fetchPostById(postId: String) {
        viewModelScope.launch {
            try {
                val postDetail = repository.getPostById(postId)
                _singlePost.value = postDetail
            } catch (e: Exception) {
                println("\"Error fetching post")

                _singlePost.value = null
            }
        }
    }

    // Method to add a like to a post
    fun addLike(postId: String) {
        viewModelScope.launch {
            try {
                val result = repository.addLike(postId)
                _likeStatus.value = result
            } catch (e: Exception) {
                _likeStatus.value = null
                // Handle the exception as needed
            }
        }
    }

    // Method to dislike a post
    fun dislikePost(postId: String) {
        viewModelScope.launch {
            try {
                val result = repository.dislikePost(postId)
                _likeStatus.value = result
            } catch (e: Exception) {
                _likeStatus.value = null
                // Handle the exception as needed
            }
        }
    }

    // Method to check if a post is liked
    fun checkIfPostLiked(postId: String) {
        viewModelScope.launch {
            try {
                val result = repository.isPostLiked(postId)
                _isPostLiked.value = result.toBoolean()
            } catch (e: Exception) {
                _isPostLiked.value = false

            }
        }
    }

    fun addCommentToPost(postId: String, commentText: String) {
        viewModelScope.launch {
            try {
                val response = repository.addComment(postId, commentText)
                if (response != null) {

                }
            } catch (e: Exception) {

            }
        }
    }
    fun deleteComment(commentId: String) {
        viewModelScope.launch {
            try {
                repository.deleteComment(commentId)
                _deleteCommentResult.value = true
            } catch (e: Exception) {
                _deleteCommentResult.value = false
            }
        }
    }

}