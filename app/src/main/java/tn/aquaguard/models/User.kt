package tn.aquaguard.models

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("username")
    var username: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("firstName")
    var firstName: String,

    @SerializedName("lastName")
    var lastName: String,

    @SerializedName("image")
    var image: String,

    @SerializedName("isActivated")
    var isActivated: Boolean,

    @SerializedName("isBlocked")
    var isBlocked: Boolean,

    @SerializedName("resetCode")
    var resetCode: Int,

    @SerializedName("nbPts")
    var nbPts: Int,

    @SerializedName("role")
    var role: Boolean,
)
