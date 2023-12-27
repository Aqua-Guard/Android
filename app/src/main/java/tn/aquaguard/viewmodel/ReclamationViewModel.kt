package tn.aquaguard.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import tn.aquaguard.models.Reclamation
import tn.aquaguard.network.SessionManager
import tn.aquaguard.repository.ReclamationRepository

class ReclamationViewModel : ViewModel() {
    private val repository = ReclamationRepository()

    fun getReclamationsByUserId(): LiveData<List<Reclamation>> {
        return liveData<List<Reclamation>> {
            val reclamationsData = repository.getReclamationsByUserId()
            emit(reclamationsData ?: emptyList())
        }
    }


    val reclamations = liveData<List<Reclamation>> {
        val ReclaationsData = repository.getAllreclamation()
        emit(ReclaationsData ?: emptyList())
    }
    fun addReclamation(title: RequestBody, description: RequestBody, image: MultipartBody.Part) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.addReclamation(title, description, image)
            }
            // Assuming you have a LiveData or a similar mechanism to handle the result
//            _addReclamationStatus.postValue(response!!)
        }
    }


}