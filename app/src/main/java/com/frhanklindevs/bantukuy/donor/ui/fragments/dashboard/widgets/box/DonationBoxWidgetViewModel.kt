package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.box

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DonationBoxWidgetViewModel(application: Application): ViewModel() {

    private val _homeName = MutableLiveData<String>()
    val homeName: LiveData<String> = _homeName

    private val _isDonateable = MutableLiveData<Boolean>()
    val isDonateable: LiveData<Boolean> = _isDonateable

    private val _currentTotalDonationMoney = MutableLiveData<Int>()
    val currentTotalDonationMoney: LiveData<Int> = _currentTotalDonationMoney

    private val _currentTotalDonationGoodsWeight = MutableLiveData<Int>()
    val currentTotalDonationGoodsWeight: LiveData<Int> = _currentTotalDonationGoodsWeight

    private val _currentTotalExpeditionFee = MutableLiveData<Int>()
    val currentTotalExpeditionFee: LiveData<Int> = _currentTotalExpeditionFee

    private val _currentTotalCost = MutableLiveData<Int>()
    val currentTotalCost: LiveData<Int> = _currentTotalCost

    init {
        //TODO: Get Home Name from Database
        _homeName.value = ""
        _isDonateable.value = !_homeName.value.isNullOrEmpty()

        if (_isDonateable.value!!) {
            //TODO: Connect to Database, Get Current total money of donation
            _currentTotalDonationMoney.value = 10000000

            //TODO: Connect to Database, get Current total weight of donation item
            _currentTotalDonationGoodsWeight.value = 12

            //TODO: Connect to Database, get current total expedition fee
            if (_currentTotalDonationGoodsWeight.value != null) {
                _currentTotalExpeditionFee.value = _currentTotalDonationGoodsWeight.value!! * 30000
            }

            //TODO: Connect to Database, get current total cost
            if (_currentTotalDonationMoney.value != null && _currentTotalExpeditionFee.value != null) {
                _currentTotalCost.value = _currentTotalDonationMoney.value!! + _currentTotalExpeditionFee.value!!
            }
        }
    }
}