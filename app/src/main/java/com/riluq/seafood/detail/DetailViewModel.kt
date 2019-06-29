package com.riluq.seafood.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.riluq.seafood.network.MealsDetail
import com.riluq.seafood.network.Seafood
import com.riluq.seafood.network.TheMealDBApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel(seafood: Seafood, barcodeResult: String, app: Application): AndroidViewModel(app)  {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _selectedSeafood = MutableLiveData<MealsDetail>()
    val selectedSeafood: LiveData<MealsDetail>
        get() = _selectedSeafood

    private val _mealYoutube = MutableLiveData<String>()
    val mealYoutube: LiveData<String>
        get() = _mealYoutube

    private val _mealSource = MutableLiveData<String>()
    val mealSource: LiveData<String>
        get() = _mealSource

    private val _navigateToYoutube = MutableLiveData<Boolean>()
    val navigateToYoutube: LiveData<Boolean>
        get() = _navigateToYoutube

    private val _navigateToSource = MutableLiveData<Boolean>()
    val navigateToSource: LiveData<Boolean>
        get() = _navigateToSource

    private val _barcodeCheck = MutableLiveData<Boolean>()
    val barcodeCheck: LiveData<Boolean>
        get() = _barcodeCheck
    private val _barcodeString = MutableLiveData<String>()

    private val _navigateToOverview = MutableLiveData<Boolean>()
    val navigateToOverview: LiveData<Boolean>
        get() = _navigateToOverview

    init {
        _navigateToOverview.value = false
        _barcodeString.value = barcodeResult
        _barcodeCheck.value = _barcodeString.value != ""
        _navigateToYoutube.value = false
        _navigateToSource.value = false
        if(_barcodeCheck.value == true) {
            getDetailTheMealDB(_barcodeString.value)
        } else {
            getDetailTheMealDB(seafood.id)
        }
    }

    private fun getDetailTheMealDB(id: String?) {
        coroutineScope.launch {
            val getDetailDeffered = TheMealDBApi.retrofitService.getMealDetailAsync(id!!)
            try {
                val listResult = getDetailDeffered.await().meals
                if (listResult.size > 0) {
                    _selectedSeafood.value = listResult[0]
                    _mealYoutube.value = listResult[0].mealYoutube
                    _mealSource.value = listResult[0].mealSource
                }

            } catch (t: Throwable) {
                Log.i("DetailViewModel", "Error: ${t.message}")
                _mealYoutube.value = null
                _mealSource.value = null
                _navigateToOverview.value = true
            }
        }
    }

    fun onYoutubeClicked() {
        if (_mealYoutube.value != null) {
            _navigateToYoutube.value = true
        }
    }
    fun onYoutubeNavigated() {
        _navigateToYoutube.value = false
        _mealYoutube.value = null
    }

    fun onSourceClicked() {
        if (_mealSource.value != null) {
            _navigateToSource.value = true
        }
    }
    fun onSourceNavigated() {
        _navigateToSource.value = false
        _mealSource.value = null
    }

    fun onOverviewNavigated() {
        _navigateToOverview.value = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}