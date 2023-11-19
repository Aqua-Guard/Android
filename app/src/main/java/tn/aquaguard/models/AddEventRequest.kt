package tn.aquaguard.models

import java.util.Date

data class AddEventRequest (
    val userId: String,
    val name: String,
    val DateDebut: Date,
    val DateFin: Date,
    val Description: String,
    val lieu: String,
    val image: String,
)