package tn.aquaguard.models

import com.google.gson.annotations.SerializedName

data class LoginResponse (

    @SerializedName("token")
    var token: String,

    @SerializedName("id")
    var id: String,

    @SerializedName("image")
    var image: String,

    @SerializedName("nbPts")
    var nbPts: Int,

    @SerializedName("username")
    var username: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("role")
    var role: String,

    @SerializedName("isActivated")
    var isActivated: Boolean,
)