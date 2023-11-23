package tn.aquaguard.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import tn.aquaguard.models.AddEventRequest
import tn.aquaguard.models.Event
import tn.aquaguard.network.RetrofitClient
import tn.aquaguard.repository.EventRepository

class EventViewModel : ViewModel() {

    var errorMessage: String by mutableStateOf("")
    var response: Response<String?>? = null

    private val _iseventDeleted = MutableLiveData<Boolean>()
    val deleteEventResult: LiveData<Boolean> = _iseventDeleted

    private val _updateEventResult = MutableLiveData<Response<String?>>()
    val updateEventResult: LiveData<Response<String?>> = _updateEventResult

    private val repository = EventRepository()

    val events = liveData {
        val eventData = repository.getAllEvents()
        emit(eventData ?: emptyList())
    }

    val myevents = liveData {
        val EventData = repository.getAllEventsByCurrentUser()
        println(EventData)
        emit(EventData ?: emptyList())
    }


    suspend fun addEvent(event: AddEventRequest) {

        val retrofit = RetrofitClient.eventService

        try {
            response = retrofit.addEvent(event)
            println(event)
            if (response?.isSuccessful == true) {
                // Check for the expected 201 status code
                if (response?.code() == 201) {
                    // Successful creation of an event
                    errorMessage = "Event added successfully!"
                } else {
                    // Handle unexpected status codes
                    errorMessage = "Unexpected status code: ${response?.code()}"
                }
            } else {
                // Handle unsuccessful response
                errorMessage = "Error: ${response?.errorBody()?.string()}"
            }

        } catch (e: Exception) {
            errorMessage = e.message.toString()
        }
    }

    suspend fun deleteEvent(eventId: String) {

        try {
            val result = repository.deleteEvent(eventId)
            _iseventDeleted.value = true
        } catch (e: Exception) {
            _iseventDeleted.value = false

        }
    }



    fun updateEvent(
        eventId: String,
        imageUri: MultipartBody.Part,
        name: RequestBody,
        description: RequestBody,
        location: RequestBody,
        startDate: RequestBody,
        endDate: RequestBody
    ) {
        viewModelScope.launch {
                val response = repository.updateEvent(
                    eventId,
                    imageUri,
                    name,
                    description,
                    location,
                    startDate,
                    endDate
                )
            _updateEventResult.postValue(response)

        }
    }
}