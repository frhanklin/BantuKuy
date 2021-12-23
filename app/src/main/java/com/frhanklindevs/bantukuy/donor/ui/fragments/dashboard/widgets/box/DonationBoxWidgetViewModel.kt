package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.box

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

class DonationBoxWidgetViewModel(application: Application): ViewModel() {

    private val repository: BantuKuyRepository = BantuKuyRepository(application)

    private val _homeId = MutableLiveData<String>()

    private val _userId = MutableLiveData<Int>()

    private val _homeName = MutableLiveData<String>()
    val homeName: LiveData<String> = _homeName

    private val _placeDetail = MutableLiveData<PlaceDetails>()
    val placeDetail : LiveData<PlaceDetails> = _placeDetail

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

    private val _donationButtonEnabled = MutableLiveData<Boolean>()
    val donationButtonEnabled: LiveData<Boolean> = _donationButtonEnabled

    private val _box = MutableLiveData<DonationBoxEntity>()
    val box : LiveData<DonationBoxEntity> = _box


    fun setUserId(userId: Int) {
        _userId.value = userId

        setBox()
    }

    fun setBox() {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            _box.value = _userId.value?.let { repository.getUserActiveBox(it) }

            if (_box.value?.placeId != null) {
                _homeId.value = _box.value?.placeId as String

                setPlace()
            }

            _isDonateable.value = !_homeId.value.isNullOrEmpty()
            _currentTotalDonationMoney.value = _box.value?.let { repository.getTotalCash(it.boxId).toInt() }
            _currentTotalDonationGoodsWeight.value = _box.value?.let {
                repository.getTotalGoodsWeight(
                    it.boxId)
            }
            if (_currentTotalDonationGoodsWeight.value != null) {

                _currentTotalExpeditionFee.value =
                    if (_currentTotalDonationGoodsWeight.value == 0) {
                        0
                    } else {
                        _box.value?.let { repository.getExpeditionServiceUsed(it.boxId) }?.let {
                            repository.getExpeditionServiceDetail(
                                it
                            ).planPricePerKg.toInt() * _currentTotalDonationGoodsWeight.value!!
                        }
                    }
                if (_currentTotalExpeditionFee.value != null && _currentTotalDonationMoney.value != null) {
                    _currentTotalCost.value = _currentTotalDonationMoney.value!! + _currentTotalExpeditionFee.value!!
                }
            }
            _donationButtonEnabled.value = if (_currentTotalCost.value != null) {
                _currentTotalCost.value!! > 0
            } else {
                false
            }
        }
    }

    private fun setPlace() {
        val client = ApiConfig.getApiService().getPlaceDetail(_homeId.value!!, API_KEY)
        client.enqueue(object : Callback<PlaceDetailResponse> {
            override fun onResponse(
                call: Call<PlaceDetailResponse>,
                response: Response<PlaceDetailResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status.equals("OK")) {
                        _placeDetail.value = response.body()?.placeDetails as PlaceDetails
                        setPlaceName()
                    }
                }
            }

            override fun onFailure(call: Call<PlaceDetailResponse>, t: Throwable) {

            }

        } )



    }

    private fun setPlaceName() {
        if (_placeDetail.value?.name != null) {
            _homeName.value = _placeDetail.value?.name as String
        }
    }

    companion object {
        private const val API_KEY = BantuKuyDev.API_KEY
    }
}