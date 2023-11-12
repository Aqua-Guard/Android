package tn.aquaguard.models

import java.util.Date

data class Post (

    val userName:String,
    val userRole:String,
    val description: String,
    val userImage: String,
    val postImage: String,
    val nbLike:Int,
    val nbComments :Int,
    val nbShare : Int,
    val comments: List<Comment>
)