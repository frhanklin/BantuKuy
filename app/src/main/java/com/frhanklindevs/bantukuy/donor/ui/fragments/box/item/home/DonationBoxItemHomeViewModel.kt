package com.frhanklindevs.bantukuy.donor.ui.fragments.box.item.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhanklindevs.bantukuy.donor.data.api.PlaceDetails

class DonationBoxItemHomeViewModel(application: Application): ViewModel() {

    private val _homeId = MutableLiveData<String>()
    val homeId: LiveData<String> = _homeId

    private val _homeDetails = MutableLiveData<PlaceDetails>()
    val homeDetails: LiveData<PlaceDetails> = _homeDetails

    init {
        //TODO: Connect Local Database
        _homeId.value = "ChIJr9XR21f0aS4Rzfs2NtCW4k8"


    }
}