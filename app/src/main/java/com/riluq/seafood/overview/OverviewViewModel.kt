package com.riluq.seafood.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.riluq.seafood.network.Seafood
import com.riluq.seafood.network.TheMealDBApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


enum class TheMealDBApiStatus {LOADING, ERROR, DONE}

class OverviewViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<TheMealDBApiStatus>()
    val status: LiveData<TheMealDBApiStatus>
        get() = _status

    private val _seafood = MutableLiveData<List<Seafood>>()
    val seafood: LiveData<List<Seafood>>
        get() = _seafood

    private val _navigateToSelectedSeafood = MutableLiveData<Seafood>()
    val navigateToSelectedSeafood: LiveData<Seafood>
        get() = _navigateToSelectedSeafood

    private val _navigateToBarcodeScanner = MutableLiveData<Boolean>()
    val navigateToBarcodeScanner: LiveData<Boolean>
        get() = _navigateToBarcodeScanner

    init {
        getSeafoodTheMealDb()
    }

    private fun getSeafoodTheMealDb() {
        coroutineScope.launch {
            val getSeafoodDeffered = TheMealDBApi.retrofitService.getSeafoodAsync("Seafood")
            try {
                _status.value = TheMealDBApiStatus.LOADING
                val listResult = getSeafoodDeffered.await().meals
                if (listResult.size > 0) {
                    _seafood.value = listResult
                }
                _status.value = TheMealDBApiStatus.DONE
            } catch (t: Throwable) {
                Log.i("OverviewViewModel", "Error: ${t.message}")
                _status.value = TheMealDBApiStatus.ERROR
                _seafood.value = ArrayList()
            }
        }
    }

    fun displaySeafoodDetails(seafood: Seafood) {
        _navigateToSelectedSeafood.value = seafood
    }

    fun displaySeafoodDetailsComplete() {
        _navigateToSelectedSeafood.value = null
    }

    fun onBarcodeClicked() {
        _navigateToBarcodeScanner.value = true
    }

    fun onBarcodeComplete() {
        _navigateToBarcodeScanner.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}