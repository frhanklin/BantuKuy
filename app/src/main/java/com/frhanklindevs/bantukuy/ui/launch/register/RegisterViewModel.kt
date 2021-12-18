package com.frhanklindevs.bantukuy.ui.launch.register

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.data.BantuKuyRepository
import com.frhanklindevs.bantukuy.data.user.UserEntity

class RegisterViewModel(application: Application): ViewModel() {

    private val repository: BantuKuyRepository = BantuKuyRepository(application)

    private val _warningText = MutableLiveData<String>()
    val warningText : LiveData<String> = _warningText

    private val _warningImg = MutableLiveData<Int>()
    val warningImg : LiveData<Int> = _warningImg

    fun isInsertable(fullName: String, email: String, userName: String, password: String, passwordVerify: String): Boolean {
        if (fullName.isEmpty()) {
            _warningImg.value = R.drawable.ic_pen
            _warningText.value = "Nama lengkap masih kosong"
            return false
        }
        if (email.isEmpty()) {
            _warningImg.value = R.drawable.ic_pen
            _warningText.value = "Email masih kosong"
            return false
        }
        if (!isEmailValid(email)) {
            _warningImg.value = R.drawable.ic_baseline_alternate_email_24
            _warningText.value = "Email tidak valid"
            return false
        }
        if (userName.isEmpty()) {
            _warningImg.value = R.drawable.ic_pen
            _warningText.value = "Username masih kosong"
            return false
        }
        if (usernameIsExists(userName)) {
            _warningImg.value = R.drawable.ic_user_slash_solid
            _warningText.value = "Username tidak tersedia"
            return false
        }
        if (password.isEmpty()) {
            _warningImg.value = R.drawable.ic_pen
            _warningText.value = "Password masih kosong"
            return false
        }
        if (!isPasswordValid(password)) {
            _warningImg.value = R.drawable.ic_baseline_password_24
            _warningText.value = "Password minimal berisi 8 karakter"
            return false
        }
        if (passwordVerify.isEmpty()) {
            _warningImg.value = R.drawable.ic_pen
            _warningText.value = "Konfirmasi password masih kosong"
            return false
        }
        if (!isPasswordValid(passwordVerify)) {
            _warningImg.value = R.drawable.ic_baseline_password_24
            _warningText.value = "Password minimal berisi 8 karakter"
            return false
        }
        if (!isPasswordSame(password, passwordVerify)) {
            _warningImg.value = R.drawable.ic_baseline_password_24
            _warningText.value = "Kedua password tidak sama"
            return false
        }

        return true
    }

    fun insertUser(fullName: String, email: String, userName: String, password: String) {
        val user = UserEntity(
            fullName = fullName,
            email = email,
            userName = userName,
            password = password
        )

        _warningImg.value = R.drawable.ic_check
        _warningText.value = "Akun berhasil dibuat"

        repository.insertUser(user)
    }

    private fun usernameIsExists(userName: String): Boolean {
        val existsInTable = repository.checkUsernameExists(userName)
        return existsInTable
    }
    private fun isEmailValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    private fun isPasswordValid(password: CharSequence): Boolean {
        return password.length >= 8
    }
    private fun isPasswordSame(password: CharSequence, passwordVerify: CharSequence): Boolean {
        return password == passwordVerify
    }

}