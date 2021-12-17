package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DonorDashboardViewModel(application: Application) : ViewModel() {

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    init {
        _username.value = "frhanklin"
    }

}