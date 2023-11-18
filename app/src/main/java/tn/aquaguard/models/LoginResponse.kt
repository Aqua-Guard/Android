package tn.aquaguard.models

import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @SerializedName("token")
    var token: String,

    @SerializedName("username")
    var username: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("role")
    var role: String,

    @SerializedName("isActivated")
    var isActivated: Boolean,
)