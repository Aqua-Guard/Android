package tn.aquaguard.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import tn.aquaguard.models.ActivationCodeResponse
import tn.aquaguard.models.LoginRequest
import tn.aquaguard.models.LoginResponse
import tn.aquaguard.models.ResetPasswordRequest
import tn.aquaguard.models.SendActivationCode
import tn.aquaguard.models.SignupRequest
import tn.aquaguard.models.ChangePassword
import tn.aquaguard.models.CompleteGoogleRequest
import tn.aquaguard.models.GoogleSignInRequest
import tn.aquaguard.models.User

interface UserService {

    @POST("/user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse?>?

    @Multipart
    @POST("/user/registerAndroidIOS")
    suspend fun signup(
        @Part("username") username: RequestBody,
        @Part("password") password: RequestBody,
        @Part("email") email: RequestBody,
        @Part("firstName") firstName: RequestBody,
        @Part("lastName") lastName: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<LoginResponse?>

    @POST("/user/sendActivationCode")
    suspend fun sendActivationCode(@Body request: SendActivationCode): Response<ActivationCodeResponse?>?

    @POST("/user/verifyCode")
    suspend fun verifyCode(@Body request: ActivationCodeResponse): Response<ActivationCodeResponse?>?

    @POST("/user/forgotPassword")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ResetPasswordRequest>

    @POST("/user/changePassword")
    suspend fun changePassword(@Body request: ChangePassword): Response<ChangePassword?>?

    @POST("/user/desactivateAccount/{id}")
    suspend fun desactivateAccount(@Path("id") id: String?)

    @Multipart
    @PUT("/user/updateProfile/{id}")
    suspend fun editProfile(
        @Path("id") id: String?,
        @Part("email") email: RequestBody,
        @Part("firstName") firstName: RequestBody,
        @Part("lastName") lastName: RequestBody,
        @Part("newUsername") newUsername: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<User?>

    @POST("/user/googleSignIn")
    suspend fun googleSignIn(@Body request: GoogleSignInRequest): Response<LoginResponse?>?

    @Multipart
    @PUT("/user/completeGoogleSignin/{id}")
    suspend fun completeGoogleSignin(
        @Path("id") id: String?,
        @Part("password") password: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<CompleteGoogleRequest?>
    companion object {
        private const val BASE_URL = "http://10.0.2.2:9090/"

        var userService: UserService? = null
        fun getInstance(): UserService {
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