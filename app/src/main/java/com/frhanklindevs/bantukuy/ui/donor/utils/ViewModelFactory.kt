package com.frhanklindevs.bantukuy.ui.donor.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.ui.donor.fragments.search.DonorSearchViewModel

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory().apply {
                    instance = this
                }
            }
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DonorSearchViewModel::class.java) -> {
                DonorSearchViewModel() as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}