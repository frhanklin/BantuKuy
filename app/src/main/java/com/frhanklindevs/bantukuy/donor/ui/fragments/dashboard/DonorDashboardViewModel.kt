package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhanklindevs.bantukuy.data.BantuKuyRepository
import com.frhanklindevs.bantukuy.data.user.UserEntity

class DonorDashboardViewModel(application: Application) : ViewModel() {

    private val repository: BantuKuyRepository = BantuKuyRepository(application)

    private val _userId = MutableLiveData<Int>()

    private val _user = MutableLiveData<UserEntity>()

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    fun setUserId(userId: Int) {
        _userId.value = userId
        setUser()
        setUsername()
    }

    private fun setUser() {
        _user.value = _userId.value?.let { repository.getUserById(it) }
    }

    private fun setUsername() {
        _username.value = _user.value?.userName!!
    }

}