package com.frhanklindevs.bantukuy.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.donor.ui.detail.DetailSearchViewModel
import com.frhanklindevs.bantukuy.donor.ui.fragments.search.DonorSearchViewModel

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
            modelClass.isAssignableFrom(DetailSearchViewModel::class.java) -> {
                DetailSearchViewModel() as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}