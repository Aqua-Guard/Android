package tn.aquaguard.models

import java.util.Date

data class Event (
    val  userName: String,
    val  userImage: String,
    val  eventName: String,
    val  description: String,
    val  eventImage: String,
    val  DateDebut: Date,
    val  DateFin: Date,
    val  lieu: String,
)