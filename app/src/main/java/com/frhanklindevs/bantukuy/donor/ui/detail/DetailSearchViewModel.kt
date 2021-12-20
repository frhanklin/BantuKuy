package com.frhanklindevs.bantukuy.donor.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhanklindevs.bantukuy.data.BantuKuyRepository
import com.frhanklindevs.bantukuy.donor.data.api.PlaceDetailResponse
import com.frhanklindevs.bantukuy.donor.data.api.PlaceDetails
import com.frhanklindevs.bantukuy.network.ApiConfig
import com.frhanklindevs.bantukuy.utils.BantuKuyDev
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSearchViewModel(application: Application): ViewModel() {

    private val repository: BantuKuyRepository = BantuKuyRepository(application)

    private val _placeDetail = MutableLiveData<PlaceDetails>()
    val placeDetail : LiveData<PlaceDetails> = _placeDetail

    private val _placeId = MutableLiveData<String>()
    val placeId : LiveData<String> = _placeId

    private val _isContentVisible = MutableLiveData<Boolean>()
    val isContentVisible: LiveData<Boolean> = _isContentVisible

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isWarned = MutableLiveData<Boolean>()
    val isWarned: LiveData<Boolean> = _isWarned

    private val _warningText = MutableLiveData<String>()
    val warningText: LiveData<String> = _warningText

    fun setPlace(id: String) {
        _placeId.value = id
        hideContent()
        showLoading()
        showWarning("Memuat data")

        val client = ApiConfig.getApiService().getPlaceDetail(id, API_KEY)
        client.enqueue(object : Callback<PlaceDetailResponse> {
            override fun onResponse(
                call: Call<PlaceDetailResponse>,
                response: Response<PlaceDetailResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.status.equals("OK")) {
                        _placeDetail.value = response.body()?.placeDetails as PlaceDetails
                        hideLoading()
                        hideWarning()
                        showContent()
                    } else {
                        hideLoading()
                        showWarning("Terjadi kesalahan memuat data")
                    }
                } else {
                    hideLoading()
                    showWarning("Terjadi kesalahan")
                }
            }

            override fun onFailure(call: Call<PlaceDetailResponse>, t: Throwable) {
                hideLoading()
                showWarning("Gagal memuat data")
            }

        } )

    }

    fun savePlaceToBox(boxId: Int) {
        repository.updatePlaceByBoxId(boxId, _placeId.value!!)
    }

    private fun showContent() {
        _isContentVisible.value = true
    }

    private fun hideContent() {
        _isContentVisible.value = false
    }

    private fun showLoading() {
        _isLoading.value = true
    }

    private fun hideLoading() {
        _isLoading.value = false
    }

    private fun showWarning(message: String) {
        _isWarned.value = true
        _warningText.value = message
    }

    private fun hideWarning() {
        _isWarned.value = false
        _warningText.value = ""
    }

    companion object {
        private const val API_KEY = BantuKuyDev.API_KEY
    }

}