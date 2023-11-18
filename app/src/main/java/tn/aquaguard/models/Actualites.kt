package tn.aquaguard.models

import java.util.Date

data class Actualites (
    val userId:String,
    val title:String,
    val description:String,
    val image:String,
    val text:String,
    val views:Long

)