package com.frhanklindevs.bantukuy.donor.ui.fragments.box.item.expedition

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhanklindevs.bantukuy.data.BantuKuyRepository
import com.frhanklindevs.bantukuy.donor.data.box.DonationBoxEntity
import com.frhanklindevs.bantukuy.donor.data.box.ExpeditionServices

class DonationBoxItemExpeditionViewModel(application: Application) : ViewModel() {
    private val repository: BantuKuyRepository = BantuKuyRepository(application)

    private val _userId = MutableLiveData<Int>()
    private val _box = MutableLiveData<DonationBoxEntity>()
    private val _boxId = MutableLiveData<Int>()
    private val _expeditionId = MutableLiveData<Int>()
    private val _expeditionDetail = MutableLiveData<ExpeditionServices>()
    val expeditionDetail: LiveData<ExpeditionServices> = _expeditionDetail

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

//                setExpedition()
                _expeditionId.value = repository.getExpeditionServiceUsed(_boxId.value!!)
                _expeditionDetail.value = repository.getExpeditionServiceDetail(_expeditionId.value!!)
            }
        }
    }

    private fun setExpedition() {
        if (_boxId.value != null) {
            _expeditionId.value = repository.getExpeditionServiceUsed(_boxId.value!!)
            setExpeditionDetail()
        }
    }

    private fun setExpeditionDetail() {
        if (_expeditionId.value != null) {
            _expeditionDetail.value = repository.getExpeditionServiceDetail(_expeditionId.value!!)
        }
    }


}