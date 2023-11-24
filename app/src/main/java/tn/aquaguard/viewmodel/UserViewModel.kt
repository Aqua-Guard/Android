package tn.aquaguard.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Part
import retrofit2.http.Path
import tn.aquaguard.models.LoginRequest
import tn.aquaguard.models.LoginResponse
import tn.aquaguard.models.SignupRequest
import tn.aquaguard.models.ChangePassword
import tn.aquaguard.models.EditProfile
import tn.aquaguard.models.SendActivationCode
import tn.aquaguard.network.SessionManager

import tn.aquaguard.network.UserService

class UserViewModel : ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var response: Response<LoginResponse?>? = null
    var responseEdit: Response<EditProfile?>? = null
    var responseDelete: Response<SendActivationCode?>? = null
    var responsePass: Response<ChangePassword?>? = null

    suspend fun login(user: LoginRequest, sessionManager: SessionManager) {

        val userService = UserService.getInstance()

        try {
            response = userService.login(user)
            sessionManager.saveToken(
                response?.body()!!.token,
                response?.body()!!.id,
                response?.body()!!.image,
                response?.body()!!.nbPts,
                response?.body()!!.username,
                response?.body()!!.role,
                response?.body()!!.email,
                response?.body()!!.isActivated
            )

        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun signup(user: SignupRequest) {
        val userService = UserService.getInstance()
        try {
            response = userService.signup(user)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun changePassword(user: ChangePassword) {
        val userService = UserService.getInstance()
        try {
            responsePass = userService.changePassword(user)

        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun deleteAccount(email: String?) {

        val userService = UserService.getInstance()
        try {
            userService.deleteAccount(email)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun editProfile(
        username: String?,
        email: String,
        firstName: String,
        lastName: String,
        newUsername: String,
        //image: MultipartBody.Part
    ) {
        val userService = UserService.getInstance()

        try {
            responseEdit =
                userService.editProfile(
                    username,
                    email,
                    firstName,
                    lastName,
                    newUsername,
                    //image
                )
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }
}
