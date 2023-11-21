package tn.aquaguard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tn.aquaguard.repository.PostRepository
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import tn.aquaguard.models.Comment
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

    private val _commentsLiveData = MutableLiveData<List<Comment>?>()
    val commentsLiveData: MutableLiveData<List<Comment>?> = _commentsLiveData

    private val _addCommentResult = MutableLiveData<String?>()
    val addCommentResult: LiveData<String?> = _addCommentResult

    private val _updateComment = MutableLiveData<String?>()
    val updateComment: LiveData<String?> = _updateComment

    val posts = liveData {
        val postData = repository.getAllPosts()
        println(postData)
        emit(postData ?: emptyList())
    }

     val myposts = liveData {
        val postData = repository.getAllPostsByCurentUser()
        println(postData)
        emit(postData ?: emptyList())
    }


    fun fetchPostById(postId: String) {
        viewModelScope.launch {
            try {
                val postDetail = repository.getPostById(postId)
                _singlePost.value = postDetail
            } catch (e: Exception) {
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
                _isPostLiked.value = result!!
            } catch (e: Exception) {
                _isPostLiked.value = false

            }
        }
    }

    fun addCommentToPost(postId: String, commentText: String) {
        viewModelScope.launch {
            try {
                     repository.addComment(postId,commentText)
                    _addCommentResult.postValue("ok")
            } catch (e: Exception) {
                    _addCommentResult.postValue("error")
            }
        }
    }

    fun updateComment(commentId: String, commentText: String) {
        viewModelScope.launch {
            try {
                val response = repository.updateComment(commentId, commentText)
                if (response.isSuccessful) {
                    _updateComment.postValue("ok")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = extractErrorMessage(errorBody)
                    _updateComment.postValue(errorMessage ?: "error")
                }
            } catch (e: Exception) {
                _updateComment.postValue("An error occurred")
            }
        }
    }


    private fun extractErrorMessage(errorBody: String?): String? {
        return try {
            val jsonObject = JSONObject(errorBody)
            val errorsArray = jsonObject.getJSONArray("errors")
            val firstErrorObject = errorsArray.getJSONObject(0)
            firstErrorObject.getString("msg")
        } catch (e: JSONException) {
            null // Return null if there is an exception parsing the error message
        }
    }

    fun deleteComment(commentId: String,postId: String) {
        viewModelScope.launch {
            try {
                repository.deleteComment(commentId)

                _deleteCommentResult.value = true
            } catch (e: Exception) {
                _deleteCommentResult.value = false
            }
        }
    }
   // private fun refreshComments(postId: String) {
    //    viewModelScope.launch {
      //      try {
     //           val comments = repository.getCommentsByIdPost(postId)
     //           _commentsLiveData.value = comments
     //       } catch (e: Exception) {

       //     }
      //  }
   // }

}