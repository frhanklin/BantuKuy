package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.donasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentBantuDonasiWidgetBinding
import com.frhanklindevs.bantukuy.donor.ui.bottomnav.BottomNavListener
import com.frhanklindevs.bantukuy.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class BantuDonasiWidget : Fragment() {

    private var _binding : FragmentBantuDonasiWidgetBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BantuDonasiWidgetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentBantuDonasiWidgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            setViewModel()
            setViewBehaviors()
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[BantuDonasiWidgetViewModel::class.java]

        viewModel.isDonateable.observe(viewLifecycleOwner, {
            binding.bantuDonasiWarning.visibility = if (it) View.GONE else View.VISIBLE
            binding.bantuDonasiContainer.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.currentDonationWeight.observe(viewLifecycleOwner, {
            binding.bantuDonasiOverviewNumber.text = it.toString()
        })
    }

    private fun setViewBehaviors() {
        val bottomNavigationView = activity?.findViewById(R.id.bottom_nav_main) as BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(BottomNavListener.getBottomNavigationListenerFragment(this))

        binding.bantuDonasiEdit.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.nav_tab_donation_box
        }

        binding.bantuDonasiAdd.setOnClickListener {
            // TODO : Show Popup (Lists: Donation item categories, TextField (weight), Add Button)
        }

    }

}