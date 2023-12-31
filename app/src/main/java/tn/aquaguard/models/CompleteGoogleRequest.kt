package tn.aquaguard.models

import com.google.gson.annotations.SerializedName

data class CompleteGoogleRequest(

    @SerializedName("image")
    var image: String,

    )