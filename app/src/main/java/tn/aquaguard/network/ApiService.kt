package tn.aquaguard.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import tn.aquaguard.models.Event
import tn.aquaguard.models.LoginRequest
import tn.aquaguard.models.LoginResponse
import tn.aquaguard.models.Post
import tn.aquaguard.models.SignupRequest
import tn.aquaguard.models.User

interface ApiService {
    @GET("/posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @GET("/events")
    suspend fun getAllEvents(): Response<List<Event>>

    @POST("/user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse?>

    @POST("/user/registerAndroidIOS")
    suspend fun signup(@Body request: SignupRequest): Response<LoginResponse?>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9090/"

        var apiService: ApiService? = null
        fun getInstance() : ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}
