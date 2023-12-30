package tn.aquaguard.models

import java.util.Date

data class Reclamation(
    val idreclamation: String,
    val userId: String,
    val title: String,
    val image:String,
    val date: String,
    val description:String
)
