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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DiscutionViewModel : ViewModel() {
    private val repository = DiscutionRepository()
    private val _ismessageDeleted = MutableLiveData<Boolean>()
    val deletemessageResult: LiveData<Boolean> = _ismessageDeleted


    private val _discutionList = MutableLiveData<List<Discution>>()
    val discutionList: LiveData<List<Discution>> get() = _discutionList


    fun getallmessagesofthisreclamation(reclamationid: String): LiveData<List<Discution>> {
        return liveData {
            val messages = repository.getallmessagesofthisreclamation(reclamationid)
            _discutionList.value = messages!!
            emit(messages ?: emptyList())
        }
    }

    fun sendmessage(reclamationid: String, message: String) {
        viewModelScope.launch {
            try {
                val sentMessage = repository.sendmessage(reclamationid, message)
                // Update the LiveData with the new message
                val dateString = "2023-01-01 12:30:00"

                // Define a date format
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())


                val specificDate = dateFormat.parse(dateString)
                val discution =Discution(reclamationId = reclamationid, message = message, userRole = "user", Id = "sds", visibility =true, createdAt =specificDate )
                _discutionList.value = _discutionList.value.orEmpty() + discution!!
            } catch (e: Exception) {
                // Handle error if needed
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