package com.riluq.seafood.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riluq.seafood.network.Seafood

class DetailViewModelFactory(
    private val seafood: Seafood,
    private val barcodeResult: String,
    private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(seafood, barcodeResult, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}