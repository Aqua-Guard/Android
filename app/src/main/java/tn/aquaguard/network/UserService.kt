package tn.aquaguard.network

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
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
import tn.aquaguard.models.EditProfile

interface UserService {

    @POST("/user/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse?>?

    @POST("/user/registerAndroidIOS")
    suspend fun signup(@Body request: SignupRequest): Response<LoginResponse?>

    @POST("/user/sendActivationCode")
    suspend fun sendActivationCode(@Body request: SendActivationCode): Response<ActivationCodeResponse?>?

    @POST("/user/verifyCode")
    suspend fun verifyCode(@Body request: ActivationCodeResponse): Response<ActivationCodeResponse?>?

    @POST("/user/forgotPassword")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<ResetPasswordRequest>

    @POST("/user/changePassword")
    suspend fun changePassword(@Body request: ChangePassword): Response<ChangePassword?>?

    @DELETE("/user/deleteUser/{email}")
    suspend fun deleteAccount(@Path("email") email: String?)

    @Multipart
    @PUT("/user/updateProfile/{username}")
    suspend fun editProfile(
        @Path("username") username: String?,
        @Part("email") email: String,
        @Part("firstName") firstName: String,
        @Part("lastName") lastName: String,
        @Part("username") newUsername: String,
        //@Part image: MultipartBody.Part?
    ): Response<EditProfile?>?

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