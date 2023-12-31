package tn.aquaguard.models

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("username")
    var username: String,

    @SerializedName("email")
    var email: String,

    @SerializedName("firstName")
    var firstName: String,

    @SerializedName("lastName")
    var lastName: String,

    @SerializedName("image")
    var image: String,

    )
