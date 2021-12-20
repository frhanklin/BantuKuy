package com.frhanklindevs.bantukuy.donor.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.ActivityDonorHomeBinding
import com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.DonorDashboardFragment
import com.frhanklindevs.bantukuy.donor.ui.fragments.box.DonorDonationBoxFragment
import com.frhanklindevs.bantukuy.donor.ui.fragments.search.DonorSearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.fragment.app.Fragment
import com.frhanklindevs.bantukuy.donor.ui.bottomnav.BottomNavListener


class DonorHomeActivity : AppCompatActivity() {

    private var _binding : ActivityDonorHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDonorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavMain.setOnNavigationItemSelectedListener(BottomNavListener.getBottomNavigationListenerActivity(this))

        supportFragmentManager.beginTransaction().add(R.id.nav_host_main, fragment3, "3").hide(fragment3).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_main, fragment2, "2").hide(fragment2).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_main, fragment1, "1").commit()

        supportActionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        val fragment1= DonorDashboardFragment()
        val fragment2= DonorSearchFragment()
        val fragment3= DonorDonationBoxFragment()
        var active : Fragment = fragment1

        const val EXTRA_USER_ID = "extra_user_id"
        const val EXTRA_BOX_ID = "extra_box_id"
    }
}