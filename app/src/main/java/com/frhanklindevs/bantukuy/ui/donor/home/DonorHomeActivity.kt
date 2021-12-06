package com.frhanklindevs.bantukuy.ui.donor.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.ActivityDonorHomeBinding

class DonorHomeActivity : AppCompatActivity() {

    private var _binding : ActivityDonorHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDonorHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBottomNavigation()
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
}