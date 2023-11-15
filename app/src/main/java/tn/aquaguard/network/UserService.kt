package tn.aquaguard.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import tn.aquaguard.models.LoginRequest
import tn.aquaguard.models.LoginResponse
import tn.aquaguard.models.SignupRequest

interface UserService {

    @POST("/user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse?>

    @POST("/user/registerAndroidIOS")
    suspend fun signup(@Body request: SignupRequest): Response<LoginResponse?>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9090/"

        var userService: UserService? = null
        fun getInstance() : UserService {
            if (userService == null) {
                userService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(UserService::class.java)
            }
            return userService!!
        }
    }
}