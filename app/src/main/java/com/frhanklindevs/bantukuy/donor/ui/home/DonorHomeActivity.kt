package com.frhanklindevs.bantukuy.donor.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.ActivityDonorHomeBinding
import com.frhanklindevs.bantukuy.donor.ui.fragments.DonorDashboardFragment
import com.frhanklindevs.bantukuy.donor.ui.fragments.DonorDonationBoxFragment
import com.frhanklindevs.bantukuy.donor.ui.fragments.search.DonorSearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment


class DonorHomeActivity : AppCompatActivity() {

    private var _binding : ActivityDonorHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDonorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setUpBottomNavigation()

        binding.bottomNavMain.setOnNavigationItemSelectedListener(bottomNavListener)



        supportFragmentManager.beginTransaction().add(R.id.nav_host_main, fragment3, "3").hide(fragment3).commit()
        supportFragmentManager.beginTransaction().add(R.id.nav_host_main, fragment2, "2").hide(fragment2).commit()

        supportFragmentManager.beginTransaction().add(R.id.nav_host_main, fragment1, "1").commit()




        supportActionBar?.hide()
    }

    private val bottomNavListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_tab_dashboard -> {
                    supportFragmentManager.beginTransaction().hide(active).show(fragment1).commit()
                    active = fragment1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_tab_search -> {
                    supportFragmentManager.beginTransaction().hide(active).show(fragment2).commit()
                    active = fragment2
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_tab_donation_box -> {
                    supportFragmentManager.beginTransaction().hide(active).show(fragment3).commit()
                    active = fragment3
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    private fun setUpBottomNavigation() {
        val bottomNav = binding.bottomNavMain
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment

        NavigationUI.setupWithNavController(
            bottomNav,
            navHostFragment.navController
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val fragment1= DonorDashboardFragment()
        private val fragment2= DonorSearchFragment()
        private val fragment3= DonorDonationBoxFragment()
        private var active : Fragment = fragment1

    }
}