package tn.aquaguard.models



data class Actualites (
    val idactualite:String,
    val userId:String,
    val title:String,
    val description:String,
    val image:String,
    val text:String,
    val views:Int,
    val like:Int,
    val dislike:Int,

)