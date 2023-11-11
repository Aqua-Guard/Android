package tn.aquaguard.models

import java.util.Date

data class Event (
    val _id:String,
    val name:String,
    val dateDebut:Date,
    val dateFin:Date,
    val description:String,
    val lieu:String,
    val image:Int
)