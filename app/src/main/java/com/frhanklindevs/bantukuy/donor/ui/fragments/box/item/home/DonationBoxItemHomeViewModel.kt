package com.frhanklindevs.bantukuy.donor.ui.fragments.box.item.home

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
import com.frhanklindevs.bantukuy.donor.data.model.SharedDonorViewModel
import com.frhanklindevs.bantukuy.network.ApiConfig
import com.frhanklindevs.bantukuy.utils.BantuKuyDev
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonationBoxItemHomeViewModel(application: Application): ViewModel() {

    private val repository: BantuKuyRepository = BantuKuyRepository(application)

    private val _userId = MutableLiveData<Int>()
    private val _box = MutableLiveData<DonationBoxEntity>()
    private val _boxId = MutableLiveData<Int>()

    private val _homeId = MutableLiveData<String>()
    private val _homeDetails = MutableLiveData<PlaceDetails>()
    private val _homeName = MutableLiveData<String>()
    val homeName : LiveData<String> = _homeName
    private val _homeAddress = MutableLiveData<String>()
    val homeAddress : LiveData<String> = _homeAddress
    private val _homeImageUrl = MutableLiveData<String>()
    val homeImageUrl: LiveData<String> = _homeImageUrl

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
            _homeId.value = _box.value!!.placeId
            val client = ApiConfig.getApiService().getPlaceDetail(_box.value!!.placeId.toString(), API_KEY)
            client.enqueue(object : Callback<PlaceDetailResponse> {
                override fun onResponse(
                    call: Call<PlaceDetailResponse>,
                    response: Response<PlaceDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status.equals("OK")) {
                            _homeDetails.value = response.body()?.placeDetails as PlaceDetails
                            _homeName.value = _homeDetails.value?.name
                            _homeAddress.value = _homeDetails.value?.formattedAddress
                            _homeImageUrl.value = getPhotoUrl(_homeDetails.value!!.photos!![0]?.photoReference!!)
                        }
                    }
                }

                override fun onFailure(call: Call<PlaceDetailResponse>, t: Throwable) {

                }

            } )

        }
    }

    private fun getPhotoUrl(photoReference: String): String? {
        return PHOTO_URL + photoReference + API_PLACEHOLDER + API_KEY
    }

    companion object {
        private const val API_KEY = BantuKuyDev.API_KEY
        private const val PHOTO_URL = BantuKuyDev.PHOTO_URL
        private const val API_PLACEHOLDER = BantuKuyDev.API_PLACEHOLDER

    }

}