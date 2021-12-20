package com.frhanklindevs.bantukuy.donor.ui.fragments.box

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
import com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.box.DonationBoxWidgetViewModel
import com.frhanklindevs.bantukuy.network.ApiConfig
import com.frhanklindevs.bantukuy.utils.BantuKuyDev
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonorDonationBoxViewModel(application: Application): ViewModel() {

    private val repository: BantuKuyRepository = BantuKuyRepository(application)

    private val _homeId = MutableLiveData<String>()

    private val _userId = MutableLiveData<Int>()


    private val _placeDetail = MutableLiveData<PlaceDetails>()
    val placeDetail : LiveData<PlaceDetails> = _placeDetail

    private val _homeName = MutableLiveData<String>()
    val homeName: LiveData<String> = _homeName

    private val _isDonateable = MutableLiveData<Boolean>()
    val isDonateable: LiveData<Boolean> = _isDonateable

    private val _box = MutableLiveData<DonationBoxEntity>()
    val box : LiveData<DonationBoxEntity> = _box


    init {
        //TODO: Get Home Name from Database
        _homeName.value = ""
        _isDonateable.value = !_homeName.value.isNullOrEmpty()

    }

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
                        setHomeName()
                    }
                }
            }

            override fun onFailure(call: Call<PlaceDetailResponse>, t: Throwable) {
                //Do nothing
            }

        } )
    }

    private fun setHomeName() {
        _homeName.value = _placeDetail.value?.name
    }

    companion object {
        private const val API_KEY = BantuKuyDev.API_KEY
    }
}