package tn.aquaguard.models

import com.google.gson.annotations.SerializedName

data class SendMessageRequest(
    @SerializedName("reclamationId") val reclamationId: String,
    @SerializedName("message") val message: String
)
