package com.frhanklindevs.bantukuy.donor.ui.fragments.search

import android.app.Application
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

class DonorSearchViewModel(application: Application): ViewModel() {

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

    private val _recyclerState = MutableLiveData<Boolean>()
    val recyclerState: LiveData<Boolean> = _recyclerState



    fun searchHomes() {
        showLoading()
        hideRecycler()

        val client = ApiConfig.getApiService().getPlaceTextSearch(queryText.value as String, API_KEY)
        client.enqueue(object : Callback<PlaceTextSearchResponse> {
            override fun onResponse(
                call: Call<PlaceTextSearchResponse>,
                response: Response<PlaceTextSearchResponse>
            ) {
                showWarning("Memuat data")

                if (response.isSuccessful) {
                    if (response.body()?.status.equals("OK")) {
                        _homesResults.value = (response.body()!!.results as List<PlaceItem>?)!!
                        hideWarning()
                        showRecycler()
                    } else {
                        showWarning("Data panti tidak ditemukan")
                    }
                } else {
                    showWarning("Terjadi kesalahan")
                }
            }

            override fun onFailure(call: Call<PlaceTextSearchResponse>, t: Throwable) {
                showWarning("Pencarian gagal")
                println("Error code: -1")

            }
        })

        hideLoading()
    }

    fun setQuery(query: String) {
        if (query.isDigitsOnly() || query.isEmpty() || query.isBlank()) {
            _queryText.value = "Panti asuhan di Jakarta"
        } else {
            _queryText.value = query
        }
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

    private fun showRecycler() {
        _recyclerState.value = true
    }

    private fun hideRecycler() {
        _recyclerState.value = false
    }

    companion object {
        private const val API_KEY = BantuKuyDev.API_KEY
    }




}