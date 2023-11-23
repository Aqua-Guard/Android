package tn.aquaguard.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import tn.aquaguard.models.Avis
import tn.aquaguard.models.SignupRequest
import tn.aquaguard.network.ActualiteService
import tn.aquaguard.network.UserService
import tn.aquaguard.repository.ActualiteRepository

class ActualiteViewModel: ViewModel() {

    private val repository =ActualiteRepository()
    var errorMessage: String by mutableStateOf("")
    var response: Response<String?>? = null

    private val _addAvisResult = MutableLiveData<String?>()
    val addAvisResult: LiveData<String?> = _addAvisResult


    val events = liveData {
        val ActualiteData = repository.getAll()
        emit(ActualiteData ?: emptyList())
    }
    fun addAvis(avis: Avis) {
        viewModelScope.launch {
            try {
                repository.addAvis(avis)
                _addAvisResult.postValue("ok")
            } catch (e: Exception) {
                _addAvisResult.postValue("error")
            }
        }
    }

}