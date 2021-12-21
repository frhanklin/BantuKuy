package com.frhanklindevs.bantukuy.utils

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.donor.ui.detail.DetailSearchViewModel
import com.frhanklindevs.bantukuy.donor.ui.fragments.box.DonorDonationBoxViewModel
import com.frhanklindevs.bantukuy.donor.ui.fragments.box.content.DonationContentViewModel
import com.frhanklindevs.bantukuy.donor.ui.fragments.box.item.home.DonationBoxItemHomeViewModel
import com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.DonorDashboardViewModel
import com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.box.DonationBoxWidgetViewModel
import com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.donasi.BantuDonasiWidgetViewModel
import com.frhanklindevs.bantukuy.donor.ui.fragments.search.DonorSearchViewModel
import com.frhanklindevs.bantukuy.ui.launch.login.LoginViewModel
import com.frhanklindevs.bantukuy.ui.launch.register.RegisterViewModel

class ViewModelFactory private constructor(private val mApplication: Application): ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (instance == null) {
                synchronized(ViewModelFactory::class.java) {
                    instance = ViewModelFactory(application)
                }
            }
            return instance as ViewModelFactory
        }

    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DonorSearchViewModel::class.java) -> {
                DonorSearchViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(DetailSearchViewModel::class.java) -> {
                DetailSearchViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(DonorDashboardViewModel::class.java) -> {
                DonorDashboardViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(BantuDonasiWidgetViewModel::class.java) -> {
                BantuDonasiWidgetViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(DonationBoxWidgetViewModel::class.java) -> {
                DonationBoxWidgetViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(DonorDonationBoxViewModel::class.java) -> {
                DonorDonationBoxViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(DonationBoxItemHomeViewModel::class.java) -> {
                DonationBoxItemHomeViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(mApplication) as T
            }
            modelClass.isAssignableFrom(DonationContentViewModel::class.java) -> {
                DonationContentViewModel(mApplication) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}