package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.donasi

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BantuDonasiWidgetViewModel(application: Application): ViewModel() {

    private val _homeName = MutableLiveData<String>()

    private val _isDonateable = MutableLiveData<Boolean>()
    val isDonateable: LiveData<Boolean> = _isDonateable

    private val _currentDonationWeight = MutableLiveData<Int>()
    val currentDonationWeight: LiveData<Int> = _currentDonationWeight

    init {
        //TODO: Connect to Database, Get Selected Home Name
        _homeName.value = ""
        _isDonateable.value = !_homeName.value.isNullOrEmpty()

        if (_isDonateable.value!!) {
            //TODO: Connect to Database, Get Current total weight of donation items
            _currentDonationWeight.value = 12
        }
    }

}