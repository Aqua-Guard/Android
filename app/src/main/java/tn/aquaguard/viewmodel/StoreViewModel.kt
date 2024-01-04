package tn.aquaguard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import tn.aquaguard.repository.ProductRepository

class StoreViewModel: ViewModel() {
    private val repository = ProductRepository()

    val products = liveData {
        val ProductData = repository.getAll()
        emit(ProductData ?: emptyList())
    }
}