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

    @SerializedName("is_activated")
    var is_active: Boolean,
)