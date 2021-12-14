package com.frhanklindevs.bantukuy.donor.ui.fragments.search

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhanklindevs.bantukuy.donor.data.PlaceItem
import com.frhanklindevs.bantukuy.donor.data.PlaceTextSearchResponse
import com.frhanklindevs.bantukuy.network.ApiConfig
import com.frhanklindevs.bantukuy.utils.BantuKuyDev
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonorSearchViewModel: ViewModel() {

    private val _queryText = MutableLiveData<String>()
    val queryText: LiveData<String> = _queryText

    private val _homesResults = MutableLiveData<List<PlaceItem>>()
    val homesResults: LiveData<List<PlaceItem>> = _homesResults

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isWarned = MutableLiveData<Boolean>()
    val isWarned: LiveData<Boolean> = _isWarned

    private val _warningText = MutableLiveData<String>()
    val warningText: LiveData<String> = _warningText

    fun searchHomes() {
        enableLoading()

        //TODO: Implement JSON API Call, set Live Data
        val client = ApiConfig.getApiService().getPlaceTextSearch(queryText.value as String, API_KEY)
        client.enqueue(object : Callback<PlaceTextSearchResponse> {
            override fun onResponse(
                call: Call<PlaceTextSearchResponse>,
                response: Response<PlaceTextSearchResponse>
            ) {

                if (response.isSuccessful) {
                    if (response.body()?.results?.isNotEmpty() as Boolean) {
                        _homesResults.value = (response.body()!!.results as List<PlaceItem>?)!!
                        disableWarning()
                    } else {
                        enableWarning("Data panti tidak ditemukan")
                    }
                } else {
                    enableWarning("Terjadi kesalahan")
                }
            }

            override fun onFailure(call: Call<PlaceTextSearchResponse>, t: Throwable) {
                disableLoading()
                enableWarning("Pencarian gagal")
            }

        })

        //Currently used: Dummy Data

        disableLoading()
    }

    fun setQuery(query: String) {
        if (query.isDigitsOnly() || query.isNullOrBlank() || query.isEmpty()) {
            _queryText.value = "Panti asuhan di Jakarta"
        } else {
            _queryText.value = query
        }
    }

    private fun enableLoading() {
        _isLoading.value = true
    }

    private fun disableLoading() {
        _isLoading.value = false
    }

    private fun enableWarning(message: String) {
        _isWarned.value = true
        _warningText.value = message
    }

    private fun disableWarning() {
        _isWarned.value = false
        _warningText.value = ""
    }

    companion object {
        private const val API_KEY = BantuKuyDev.API_KEY
    }




}