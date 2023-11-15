package tn.aquaguard.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import retrofit2.Response
import tn.aquaguard.models.LoginRequest
import tn.aquaguard.models.LoginResponse
import tn.aquaguard.models.SignupRequest

import tn.aquaguard.network.ApiService

class UserViewModel : ViewModel(){

    var errorMessage: String by mutableStateOf("")
    var response: Response<LoginResponse?>? = null

    suspend fun login(user: LoginRequest){

        val apiService = ApiService.getInstance()

        try {
            response = apiService.login(user)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun signup(user: SignupRequest){
        val apiService = ApiService.getInstance()
        try {
            response = apiService.signup(user)
        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }
}
