package com.frhanklindevs.bantukuy.ui.launch.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.data.BantuKuyRepository
import com.frhanklindevs.bantukuy.data.user.UserEntity

class LoginViewModel(application: Application): ViewModel() {

    private val repository: BantuKuyRepository = BantuKuyRepository(application)

    private val _warningText = MutableLiveData<String>()
    val warningText : LiveData<String> = _warningText

    private val _warningImg = MutableLiveData<Int>()
    val warningImg : LiveData<Int> = _warningImg

    fun verifyUser(username: String, password: String): Boolean {
        val user = repository.getUser(username)

        return if (user.value?.get(0) != null) {
            if (user.value!!.get(0).password == password) {
                true
            } else {
                _warningText.value = "Password salah."
                _warningImg.value = R.drawable.ic_close_white
                false
            }
        } else {
            _warningText.value = "User tidak ditemukan."
            _warningImg.value = R.drawable.ic_close_white
            false
        }
    }

    fun userSearchable(username: String, password: String): Boolean {
        if (username.isEmpty()) {
            _warningImg.value = R.drawable.ic_pen
            _warningText.value = "Username kosong"
            return false
        }
        if (password.isEmpty()) {
            _warningImg.value = R.drawable.ic_pen
            _warningText.value = "Password kosong"
            return false
        }
        return true


    }

}