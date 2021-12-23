package com.frhanklindevs.bantukuy.donor.ui.fragments.box.content

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
import com.frhanklindevs.bantukuy.donor.data.box.DonationCashItems
import com.frhanklindevs.bantukuy.donor.data.box.DonationGoodsItems
import com.frhanklindevs.bantukuy.donor.data.box.ExpeditionServices
import com.frhanklindevs.bantukuy.network.ApiConfig
import com.frhanklindevs.bantukuy.utils.BantuKuyDev
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DonationContentViewModel(application: Application): ViewModel() {

    private val repository: BantuKuyRepository = BantuKuyRepository(application)

    private val _listMoney = MutableLiveData<List<DonationCashItems>>()
    val listMoney: LiveData<List<DonationCashItems>> = _listMoney

    private val _listGoods = MutableLiveData<List<DonationGoodsItems>>()
    val listGoods: LiveData<List<DonationGoodsItems>> = _listGoods

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int> = _userId

    private val _box = MutableLiveData<DonationBoxEntity>()
    val box: LiveData<DonationBoxEntity> = _box

    private val _boxId = MutableLiveData<Int>()
    val boxId: LiveData<Int> = _boxId

    private val _homeId = MutableLiveData<String>()
    private val _homeDetails = MutableLiveData<PlaceDetails>()
    private val _homeName = MutableLiveData<String>()
    val homeName : LiveData<String> = _homeName
    private val _homeAddress = MutableLiveData<String>()
    val homeAddress : LiveData<String> = _homeAddress
    private val _homeImageUrl = MutableLiveData<String>()
    val homeImageUrl: LiveData<String> = _homeImageUrl

    private val _expeditionId = MutableLiveData<Int>()
    private val _expeditionDetail = MutableLiveData<ExpeditionServices>()
    val expeditionDetail: LiveData<ExpeditionServices> = _expeditionDetail

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


    fun setUserId(userId: Int) {
        _userId.value = userId

        setBox()
    }

    fun setBox() {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            _box.value = _userId.value?.let { repository.getUserActiveBox(it) }
            if (_box.value != null) {
                _boxId.value = _box.value!!.boxId

                _expeditionId.value = repository.getExpeditionServiceUsed(_boxId.value!!)
                _expeditionDetail.value = repository.getExpeditionServiceDetail(_expeditionId.value!!)
                initializeItemLists()
                setPlace()
                setOverview()
            }
        }
    }

    private fun setOverview() {
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
        _isDonateable.value = if (_currentTotalCost.value != null) {
            _currentTotalCost.value!! > 0
        } else {
            false
        }
    }

    private fun initializeItemLists() {
        _listMoney.value = _boxId.value?.let { repository.getAllCashItems(it) }
        _listGoods.value = _boxId.value?.let { repository.getAllGoodsItems(it) }
    }

    private fun setPlace() {
        if (_box.value?.placeId != null) {
            _homeId.value = _box.value!!.placeId!!
            val client = ApiConfig.getApiService().getPlaceDetail(_box.value!!.placeId.toString(), API_KEY)
            client.enqueue(object : Callback<PlaceDetailResponse> {
                override fun onResponse(
                    call: Call<PlaceDetailResponse>,
                    response: Response<PlaceDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status.equals("OK")) {
                            _homeDetails.value = response.body()?.placeDetails as PlaceDetails
                            _homeName.value = _homeDetails.value?.name!!
                            _homeAddress.value = _homeDetails.value?.formattedAddress!!
                            _homeImageUrl.value = getPhotoUrl(_homeDetails.value!!.photos!![0]?.photoReference!!)
                        }
                    }
                }

                override fun onFailure(call: Call<PlaceDetailResponse>, t: Throwable) {

                }

            } )

        }
    }

    private fun getPhotoUrl(photoReference: String): String {
        return PHOTO_URL + photoReference + API_PLACEHOLDER + API_KEY
    }

    fun insertOrUpdateCash(categoryName: String, value: Double) {
        repository.insertOrUpdateCash(_boxId.value!!, categoryName, value)
        initializeItemLists()
        setOverview()
    }

    fun insertOrUpdateGoods(categoryName: String, value: Int) {
        repository.insertOrUpdateGoods(_boxId.value!!, categoryName, value)
        initializeItemLists()
        setOverview()
    }

    fun updateCash(newCash: DonationCashItems) {
        repository.updateCash(newCash)
        initializeItemLists()
        setOverview()
    }

    fun updateGoods(newGoods: DonationGoodsItems) {
        repository.updateGoods(newGoods)
        initializeItemLists()
        setOverview()
    }

    fun deleteCash(cashItem: DonationCashItems) {
        repository.deleteCash(cashItem)
        initializeItemLists()
        setOverview()
    }

    fun deleteGoods(goodsItem: DonationGoodsItems) {
        repository.deleteGoods(goodsItem)
        initializeItemLists()
        setOverview()
    }

    companion object {
        private const val API_KEY = BantuKuyDev.API_KEY
        private const val PHOTO_URL = BantuKuyDev.PHOTO_URL
        private const val API_PLACEHOLDER = BantuKuyDev.API_PLACEHOLDER

    }
}