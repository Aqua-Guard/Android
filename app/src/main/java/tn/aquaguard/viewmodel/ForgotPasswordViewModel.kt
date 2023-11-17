package tn.aquaguard.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Response
import tn.aquaguard.models.ActivationCodeResponse
import tn.aquaguard.models.LoginResponse
import tn.aquaguard.models.SendActivationCode
import tn.aquaguard.network.UserService

class ForgotPasswordViewModel : ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var response: Response<ActivationCodeResponse?>? = null
    lateinit var resetResponse: Response<ActivationCodeResponse>
    lateinit var email: String

    lateinit var password: String
    lateinit var confirmPassword: String


    suspend fun sendEmail(email: SendActivationCode) {

        val userService = UserService.getInstance()

        try {
            response = userService.sendActivationCode(email)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun verifyCode(verifyCode: ActivationCodeResponse){
        val userService = UserService.getInstance()

        try {
            response = userService.verifyCode(verifyCode)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }
}