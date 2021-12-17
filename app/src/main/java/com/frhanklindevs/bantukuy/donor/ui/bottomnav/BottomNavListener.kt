package com.frhanklindevs.bantukuy.donor.ui.bottomnav

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
object BottomNavListener {

    fun getBottomNavigationListenerActivity(activity: AppCompatActivity): BottomNavigationView.OnNavigationItemSelectedListener {
        val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_tab_dashboard -> {
                    activity.supportFragmentManager.beginTransaction().hide(DonorHomeActivity.active).show(
                        DonorHomeActivity.fragment1
                    ).commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_tab_search -> {
                    activity.supportFragmentManager.beginTransaction().hide(DonorHomeActivity.active).show(
                        DonorHomeActivity.fragment2
                    ).commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment2
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_tab_donation_box -> {
                    activity.supportFragmentManager.beginTransaction().hide(DonorHomeActivity.active).show(
                        DonorHomeActivity.fragment3
                    ).commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment3
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
        return listener
    }

    fun getBottomNavigationListenerFragment(fragment: Fragment): BottomNavigationView.OnNavigationItemSelectedListener {
        val listener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_tab_dashboard -> {
                    fragment.activity?.supportFragmentManager?.beginTransaction()?.hide(DonorHomeActivity.active)?.show(
                        DonorHomeActivity.fragment1
                    )?.commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_tab_search -> {
                    fragment.activity?.supportFragmentManager?.beginTransaction()?.hide(DonorHomeActivity.active)?.show(
                        DonorHomeActivity.fragment2
                    )?.commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment2
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_tab_donation_box -> {
                    fragment.activity?.supportFragmentManager?.beginTransaction()?.hide(DonorHomeActivity.active)?.show(
                        DonorHomeActivity.fragment3
                    )?.commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment3
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
        return listener
    }

}
