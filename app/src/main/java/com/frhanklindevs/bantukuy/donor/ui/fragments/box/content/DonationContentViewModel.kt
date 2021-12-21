package com.frhanklindevs.bantukuy.donor.ui.fragments.box.content

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhanklindevs.bantukuy.data.BantuKuyRepository
import com.frhanklindevs.bantukuy.donor.data.box.DonationBoxEntity
import com.frhanklindevs.bantukuy.donor.data.box.DonationCashItems
import com.frhanklindevs.bantukuy.donor.data.box.DonationGoodsItems
import com.frhanklindevs.bantukuy.utils.BantuKuyDev

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

                initializeItemLists()
            }
        }
    }

    private fun initializeItemLists() {
        _listMoney.value = _boxId.value?.let { repository.getAllCashItems(it) }
        _listGoods.value = _boxId.value?.let { repository.getAllGoodsItems(it) }
    }

    fun insertOrUpdateCash(categoryName: String, value: Double) {
        repository.insertOrUpdateCash(_boxId.value!!, categoryName, value)
        setBox()
    }

    fun updateCash(newCash: DonationCashItems) {
        repository.updateCash(newCash)
        setBox()
    }

    companion object {
        private const val API_KEY = BantuKuyDev.API_KEY
    }
}