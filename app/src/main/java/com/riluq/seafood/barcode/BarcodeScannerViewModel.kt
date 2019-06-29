package com.riluq.seafood.barcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riluq.seafood.network.Seafood

class BarcodeScannerViewModel: ViewModel() {

    private val _navigateForResult = MutableLiveData<String>()
    val navigateForResult: LiveData<String>
        get() = _navigateForResult

    init {
        _navigateForResult.value = null
    }

    fun displayMealDetails(resultBarcode: String) {
        _navigateForResult.value = resultBarcode
    }

    fun displayMealDetailsComplete() {
        _navigateForResult.value = null
    }


}