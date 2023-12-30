package tn.aquaguard.models

import java.util.Date

data class Reclamation(
    val idreclamation: String,
    val userId: String,
    val title: String,
    val image:String,
    val answered:Boolean,
    val date: String,
    val description:String
)
