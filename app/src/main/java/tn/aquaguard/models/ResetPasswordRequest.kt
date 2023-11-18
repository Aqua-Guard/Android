package tn.aquaguard.models

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest (
    @SerializedName("email")
    var email: String?,

    @SerializedName("newPassword")
    var newPassword: String,

    @SerializedName("confirmPassword")
    var confirmPassword: String
)