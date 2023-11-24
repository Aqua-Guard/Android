package tn.aquaguard.models

import java.util.Date

data class Comment (
    val idComment :String,
    val idUser :String,
    val idPost :String,
    val commentAvatar :String,
    val commentUsername : String,
    val comment: String,
)