package tn.aquaguard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tn.aquaguard.models.Participation
import tn.aquaguard.repository.ParticipationRepository


class ParticipationViewModel: ViewModel() {

    private val repository = ParticipationRepository()
    private val _singleParticipation = MutableLiveData<List<Participation>>()

    private val _isEventParticipation = MutableLiveData<Boolean>()
    val isEventParticipation: LiveData<Boolean> = _isEventParticipation


    val participations = liveData {
        val participationData = repository.getParticiptations()
        emit(participationData ?: emptyList())
    }

   /* fun getParticipations(userId: String){

        viewModelScope.launch {
            try {
                val result = repository.getParticiptations(userId)
                _singleParticipation.value = result
            } catch (e: Exception) {
                _singleParticipation.value = null
                // Handle the exception as needed
            }
        }
    }*/


    fun addParticipation(eventId : String){
        viewModelScope.launch {
            try {
                val result = repository.addParticipation(eventId)
                print(result)

            }catch (e: Exception) {
                print("error add participation")
            }
        }
    }



    fun checkIfEventParticip(eventId: String?) {
        viewModelScope.launch {
            try {
                val result = repository.isParticip(eventId)
                _isEventParticipation.value = result!!
            } catch (e: Exception) {
                _isEventParticipation.value = false

            }
        }
    }
}