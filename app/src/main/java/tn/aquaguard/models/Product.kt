package tn.aquaguard.models

data class Product(
val id: String,
val name: String,
val description: String,
var image: String,
val price: Number,
val category: String,
val quantity :Number,
)

