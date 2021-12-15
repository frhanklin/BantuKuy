package com.frhanklindevs.bantukuy.donor.ui.fragments.box

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.ActivityDonorHomeBinding
import com.frhanklindevs.bantukuy.databinding.FragmentDonorDonationBoxBinding
import com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.DonorDashboardFragment
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity

import com.google.android.material.bottomnavigation.BottomNavigationView




class DonorDonationBoxFragment : Fragment() {

    private var _binding: FragmentDonorDonationBoxBinding? = null
    private val binding get() = _binding!!

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDonorDonationBoxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewFunctions()
    }

    private fun setViewFunctions() {
        bottomNavigationView = activity?.findViewById(R.id.bottom_nav_main) as BottomNavigationView

        if (binding.dboxWarningBtn.isVisible) {
            binding.dboxWarningBtn.setOnClickListener {
                bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavListener)
                bottomNavigationView.selectedItemId = R.id.nav_tab_search
            }
        }
        binding.dboxWarningBtn
    }

    private val bottomNavListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_tab_dashboard -> {
                    activity?.supportFragmentManager?.beginTransaction()?.hide(DonorHomeActivity.active)?.show(
                        DonorHomeActivity.fragment1
                    )?.commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_tab_search -> {
                    activity?.supportFragmentManager?.beginTransaction()?.hide(DonorHomeActivity.active)?.show(
                        DonorHomeActivity.fragment2
                    )?.commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment2
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_tab_donation_box -> {
                    activity?.supportFragmentManager?.beginTransaction()?.hide(DonorHomeActivity.active)?.show(
                        DonorHomeActivity.fragment3
                    )?.commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment3
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }



}