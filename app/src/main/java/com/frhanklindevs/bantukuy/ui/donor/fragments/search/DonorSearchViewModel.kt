package com.frhanklindevs.bantukuy.ui.donor.fragments.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhanklindevs.bantukuy.ui.donor.data.PlaceItem
import com.frhanklindevs.bantukuy.ui.donor.utils.DummyData

class DonorSearchViewModel: ViewModel() {

    private val _homesResults = MutableLiveData<ArrayList<PlaceItem>>()
    val homesResults: LiveData<ArrayList<PlaceItem>> = _homesResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchHomes(query: String) {
        _isLoading.value = true

        //TODO: Implement JSON API Call, set Live Data
        //Currently used: Dummy Data

        _isLoading.value = false
    }

    fun searchHomes() {

    }




}