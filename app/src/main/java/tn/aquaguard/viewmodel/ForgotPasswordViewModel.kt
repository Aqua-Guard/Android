package tn.aquaguard.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody
import retrofit2.Response
import tn.aquaguard.models.ActivationCodeResponse
import tn.aquaguard.models.LoginResponse
import tn.aquaguard.models.ResetPasswordRequest
import tn.aquaguard.models.SendActivationCode
import tn.aquaguard.network.UserService

class ForgotPasswordViewModel : ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var response: Response<ActivationCodeResponse?>? = null
    lateinit var resetResponse: Response<ResetPasswordRequest>
    lateinit var email: String



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

    suspend fun resetPassword(resetPasswordRequest: ResetPasswordRequest){
        val userService = UserService.getInstance()

        try {
            resetResponse = userService.resetPassword(resetPasswordRequest)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }
}