package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.donasi

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhanklindevs.bantukuy.data.BantuKuyRepository
import com.frhanklindevs.bantukuy.donor.data.box.DonationBoxEntity

class BantuDonasiWidgetViewModel(application: Application): ViewModel() {

    private val repository: BantuKuyRepository = BantuKuyRepository(application)

    private val _homeId = MutableLiveData<String>()

    private val _userId = MutableLiveData<Int>()

    private val _isDonateable = MutableLiveData<Boolean>()
    val isDonateable: LiveData<Boolean> = _isDonateable

    private val _currentDonationWeight = MutableLiveData<Int>()
    val currentDonationWeight: LiveData<Int> = _currentDonationWeight

    private val _box = MutableLiveData<DonationBoxEntity>()
    val box : LiveData<DonationBoxEntity> = _box

    fun setUserId(userId: Int) {
        _userId.value = userId
        setBox()
    }

    private fun setBox() {
        println("user id: ${_userId.value}")
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            _box.value = _userId.value?.let { repository.getUserActiveBox(it) }
            _homeId.value = _box.value?.placeId
            _isDonateable.value = !_homeId.value.isNullOrEmpty()
            _currentDonationWeight.value = repository.getTotalGoodsWeight(_box.value!!.boxId)
        }
    }

    fun insertOrUpdateGoods(categoryName: String, value: Int) {
        repository.insertOrUpdateGoods(_box.value!!.boxId, categoryName, value)
        setBox()
    }

}