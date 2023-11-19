package tn.aquaguard.models

import com.google.gson.annotations.SerializedName

data class ChangePassword(

    @SerializedName("email")
    var email: String?,

    @SerializedName("oldPassword")
    var oldPassword: String,

    @SerializedName("newPassword")
    var newPassword: String,

    @SerializedName("confirmPassword")
    var confirmPassword: String
)
