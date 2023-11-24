package tn.aquaguard.models

import com.google.gson.annotations.SerializedName

data class SendActivationCode(
    @SerializedName("email")
    val email: String
)