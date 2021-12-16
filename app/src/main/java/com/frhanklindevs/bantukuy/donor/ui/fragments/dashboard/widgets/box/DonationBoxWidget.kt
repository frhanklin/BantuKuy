package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.box

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonationBoxWidgetBinding
import com.frhanklindevs.bantukuy.donor.ui.bottomnav.BottomNavListener
import com.google.android.material.bottomnavigation.BottomNavigationView

class DonationBoxWidget : Fragment() {

    private var _binding : FragmentDonationBoxWidgetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentDonationBoxWidgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewBehaviors()
    }

    private fun setViewBehaviors() {

        val bottomNavigationView = activity?.findViewById(R.id.bottom_nav_main) as BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(BottomNavListener.getBottomNavigationListenerFragment(this))

        binding.boxBtnSetTargetHouse.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.nav_tab_search
        }

        binding.boxBtnSetDetails.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.nav_tab_donation_box
        }

        binding.boxBtnDonate.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.nav_tab_donation_box
        }

    }


}