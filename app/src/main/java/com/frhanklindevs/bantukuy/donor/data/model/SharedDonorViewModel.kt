package com.frhanklindevs.bantukuy.donor.data.model

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhanklindevs.bantukuy.data.BantuKuyRepository
import com.frhanklindevs.bantukuy.donor.data.api.PlaceDetailResponse
import com.frhanklindevs.bantukuy.donor.data.api.PlaceDetails
import com.frhanklindevs.bantukuy.donor.data.box.DonationBoxEntity
import com.frhanklindevs.bantukuy.network.ApiConfig
import com.frhanklindevs.bantukuy.utils.BantuKuyDev
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SharedDonorViewModel(application: Application): ViewModel() {

    private val repository: BantuKuyRepository = BantuKuyRepository(application)

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val _boxId = MutableLiveData<Int>()
    val boxId: LiveData<Int> = _boxId

    private val _box = MutableLiveData<DonationBoxEntity>()
    val box: LiveData<DonationBoxEntity> = _box

    private val _homeDetails = MutableLiveData<PlaceDetails>()
    val homeDetails: LiveData<PlaceDetails> = _homeDetails

    private val _isDonateable = MutableLiveData<Boolean>()
    val isDonateable: LiveData<Boolean> = _isDonateable

    private val _currentTotalDonationMoney = MutableLiveData<Int>()
    val currentTotalDonationMoney: LiveData<Int> = _currentTotalDonationMoney

    private val _currentDonationWeight = MutableLiveData<Int>()
    val currentDonationWeight: LiveData<Int> = _currentDonationWeight

    private val _currentTotalExpeditionFee = MutableLiveData<Int>()
    val currentTotalExpeditionFee: LiveData<Int> = _currentTotalExpeditionFee

    private val _currentTotalCost = MutableLiveData<Int>()
    val currentTotalCost: LiveData<Int> = _currentTotalCost

    fun setUserId(userId: Int) {
        _userId.value = userId

        setBox()
    }

    private fun setBox() {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            _box.value = _userId.value?.let { repository.getUserActiveBox(it) }
            if (_box.value != null) {
                _boxId.value = _box.value!!.boxId

                setPlace()

            }
        }
    }

    private fun setPlace() {
        if (_box.value?.placeId != null) {
            val client = ApiConfig.getApiService().getPlaceDetail(_box.value!!.placeId.toString(),API_KEY)
            client.enqueue(object : Callback<PlaceDetailResponse> {
                override fun onResponse(
                    call: Call<PlaceDetailResponse>,
                    response: Response<PlaceDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status.equals("OK")) {
                            _homeDetails.value = response.body()?.placeDetails as PlaceDetails
                            setDonateable()
                        }
                    }
                }

                override fun onFailure(call: Call<PlaceDetailResponse>, t: Throwable) {

                }

            } )
        }
    }

    private fun setDonateable() {
        if (_homeDetails.value != null) {
            _isDonateable.value = true

            setBoxDetails()
        }
    }

    private fun setBoxDetails() {
        if (_boxId.value != null) {
            with(_boxId.value!!) {
                _currentTotalDonationMoney.value = repository.getTotalCash(this).toInt()
                _currentDonationWeight.value = repository.getTotalGoodsWeight(this)

                if (_currentDonationWeight.value != null) {
                    val expeditionUsed = repository.getExpeditionServiceUsed(this)

                    _currentTotalExpeditionFee.value = repository.getExpeditionServiceDetail(expeditionUsed).planPricePerKg.toInt() * _currentDonationWeight.value!!

                    if (_currentTotalExpeditionFee.value != null && _currentTotalDonationMoney.value != null) {
                        _currentTotalCost.value = _currentTotalDonationMoney.value!! + _currentTotalExpeditionFee.value!!
                    }
                }
            }
        }
    }


    companion object {
        private const val API_KEY = BantuKuyDev.API_KEY
    }
}