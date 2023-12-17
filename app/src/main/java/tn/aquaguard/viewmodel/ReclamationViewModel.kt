package tn.aquaguard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import tn.aquaguard.models.Reclamation
import tn.aquaguard.repository.ReclamationRepository

class ReclamationViewModel : ViewModel() {
    private val repository = ReclamationRepository()


    val reclamations = liveData<List<Reclamation>> {
        val ReclaationsData = repository.getAllreclamation()
        emit(ReclaationsData ?: emptyList())
    }


}