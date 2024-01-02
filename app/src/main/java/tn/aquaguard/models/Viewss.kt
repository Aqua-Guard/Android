package tn.aquaguard.models
import com.google.gson.annotations.SerializedName
data class Viewss(

    @SerializedName("_id")
    val Id: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("actualiteId")
    val actualiteId: String,
    @SerializedName("like")
    val like: Int,
    @SerializedName("comment")
    val comment: String,


)
