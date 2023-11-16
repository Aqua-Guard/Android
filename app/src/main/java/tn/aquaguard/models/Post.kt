package tn.aquaguard.models

import java.util.Date

data class Post (
    val idPost: String,
    val userName:String,
    val userRole:String,
    val description: String,
    val userImage: String,
    val postImage: String,
    val nbLike:Int,
    val nbComments :Int,
    val nbShare : Int,
    val likes: List<Like>,
    val comments: List<Comment>
)