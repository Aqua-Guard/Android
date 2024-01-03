package tn.aquaguard.models

import com.google.gson.annotations.SerializedName

data class GoogleSignInRequest(

    @SerializedName("idToken")
    var idToken: String,

    @SerializedName("username")
    var username: String,
)