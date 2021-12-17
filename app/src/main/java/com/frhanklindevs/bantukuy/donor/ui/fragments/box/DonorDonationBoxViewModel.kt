package com.frhanklindevs.bantukuy.donor.ui.fragments.box

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DonorDonationBoxViewModel(application: Application): ViewModel() {

    private val _homeName = MutableLiveData<String>()
    val homeName: LiveData<String> = _homeName

    private val _isDonateable = MutableLiveData<Boolean>()
    val isDonateable: LiveData<Boolean> = _isDonateable

    init {
        //TODO: Get Home Name from Database
        _homeName.value = ""
        _isDonateable.value = !_homeName.value.isNullOrEmpty()

    }
}