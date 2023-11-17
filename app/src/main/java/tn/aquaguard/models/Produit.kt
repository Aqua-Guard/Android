package tn.aquaguard.models

data class Product(
    val productname: String,
    val nbPoints: Int,
    val productquantite: Int,
    val productdescription: String,
    val isEnabled: Boolean,
    val productimage:String
)
