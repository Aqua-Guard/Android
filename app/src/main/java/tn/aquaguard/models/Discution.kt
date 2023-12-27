package tn.aquaguard.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Discution(
    @SerializedName("idmessage")
    val Id: String,
    @SerializedName("idreclamation")
    val reclamationId: String,
    @SerializedName("userRole")
    val userRole: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("createdAt")
    val createdAt: Date,
    @SerializedName("visibility")
    val visibility: Boolean,





)
