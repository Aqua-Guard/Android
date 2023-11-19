package tn.aquaguard.models

import com.google.gson.annotations.SerializedName

data class ActivationCodeResponse(
    @SerializedName("email")
    var email: String?,

    @SerializedName("resetCode")
    var resetCode: Int,
)
