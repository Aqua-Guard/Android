package tn.aquaguard.models

data class SignupRequest (
    var username: String,
    var password: String,
    var email: String,
    var firstName: String,
    var lastName: String,

)