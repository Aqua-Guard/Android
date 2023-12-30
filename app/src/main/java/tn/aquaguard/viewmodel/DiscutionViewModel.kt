package tn.aquaguard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import tn.aquaguard.models.Discution
import tn.aquaguard.models.Reclamation
import tn.aquaguard.models.SearchRequest
import tn.aquaguard.repository.DiscutionRepository

class DiscutionViewModel : ViewModel() {
    private val repository = DiscutionRepository()
    private val _ismessageDeleted = MutableLiveData<Boolean>()
    val deletemessageResult: LiveData<Boolean> = _ismessageDeleted

    fun getallmessagesofthisreclamation(reclamationid: String): LiveData<List<Discution>> {
        return liveData {
            val reclamationsData = repository.getallmessagesofthisreclamation(reclamationid)

            emit(reclamationsData ?: emptyList())
        }
    }

    fun sendmessage(reclamationid: String, message: String) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                repository.sendmessage(reclamationid, message)
            }
        }

    }

    suspend fun deleteonlyforuser(id: String) {

        try {
            val result = repository.deleteonlyforuser(id)
            _ismessageDeleted.value = true
        } catch (e: Exception) {
            _ismessageDeleted.value = false

        }
    }

    suspend fun deleteforall(id: String) {

        try {
            val result = repository.deleteonlyforuser(id)
            _ismessageDeleted.value = true
        } catch (e: Exception) {
            _ismessageDeleted.value = false

        }
    }


}