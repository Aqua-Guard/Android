package tn.aquaguard.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import tn.aquaguard.models.LoginRequest
import tn.aquaguard.models.LoginResponse
import tn.aquaguard.models.SignupRequest
import tn.aquaguard.models.ChangePassword
import tn.aquaguard.models.CompleteGoogleRequest
import tn.aquaguard.models.GoogleSignInRequest
import tn.aquaguard.models.User
import tn.aquaguard.network.SessionManager

import tn.aquaguard.network.UserService

class UserViewModel : ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var response: Response<LoginResponse?>? = null
    var responseEdit: Response<User?>? = null
    lateinit var responseGoogle: Response<CompleteGoogleRequest?>
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
                response?.body()!!.isActivated,
                response?.body()!!.firstName,
                response?.body()!!.lastName
            )

        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun signup(
        username: RequestBody,
        password: RequestBody,
        email: RequestBody,
        firstName: RequestBody,
        lastName: RequestBody,
        image: MultipartBody.Part) {
        val userService = UserService.getInstance()
        try {
            response = userService.signup(
                username,
                password,
                email,
                firstName,
                lastName,
                image)

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

    suspend fun desactivateAccount(id: String?) {

        val userService = UserService.getInstance()
        try {
            userService.desactivateAccount(id)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun editProfile(
        id: String?,
        email: RequestBody,
        firstName: RequestBody,
        lastName: RequestBody,
        newUsername: RequestBody,
        image: MultipartBody.Part,
        sessionManager: SessionManager
    ) {
        val userService = UserService.getInstance()

        try {
            responseEdit =
                userService.editProfile(
                    id,
                    email,
                    firstName,
                    lastName,
                    newUsername,
                    image
                )
            sessionManager.updateUser(
                responseEdit?.body()!!.image,
                responseEdit?.body()!!.username,
                responseEdit?.body()!!.email,
                responseEdit?.body()!!.firstName,
                responseEdit?.body()!!.lastName
            )
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun googleSignIn(user: GoogleSignInRequest, sessionManager: SessionManager) {
        val userService = UserService.getInstance()
        try {
            response = userService.googleSignIn(user)

            sessionManager.saveToken(
                response?.body()!!.token,
                response?.body()!!.id,
                response?.body()!!.image,
                response?.body()!!.nbPts,
                response?.body()!!.username,
                response?.body()!!.role,
                response?.body()!!.email,
                response?.body()!!.isActivated,
                response?.body()!!.firstName,
                response?.body()!!.lastName
            )

            Log.e("imaga---------------------", sessionManager.getImage().toString())

        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun completeGoogleSignin(
        id: String?,
        password: RequestBody,
        image: MultipartBody.Part,
        sessionManager: SessionManager
    ) {
        val userService = UserService.getInstance()

        try {
            responseGoogle =
                userService.completeGoogleSignin(
                    id,
                    password,
                    image
                )

            sessionManager.setImage(
                responseGoogle?.body()!!.image,
            )
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

}
