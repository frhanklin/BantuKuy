package com.frhanklindevs.bantukuy.donor.ui.fragments.box

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonorDonationBoxBinding
import com.frhanklindevs.bantukuy.donor.ui.bottomnav.BottomNavListener
import com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.box.DonationBoxWidgetViewModel
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity
import com.frhanklindevs.bantukuy.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class DonorDonationBoxFragment : Fragment() {

    private var _binding: FragmentDonorDonationBoxBinding? = null
    private val binding get() = _binding!!

    private var userId: Int = 0
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewModel: DonorDonationBoxViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentDonorDonationBoxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            userId = requireActivity().intent.getIntExtra(DonorHomeActivity.EXTRA_USER_ID, 0)
            setViewModel()
        }
        setViewFunctions()
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[DonorDonationBoxViewModel::class.java]

        viewModel.isDonateable.observe(viewLifecycleOwner, {
            binding.dboxWarningContainer.visibility = if (it) View.GONE else View.VISIBLE
            binding.dboxAllContents.visibility = if (it) View.VISIBLE else View.GONE
        })


        viewModel.setUserId(userId)
    }

    private fun setViewFunctions() {
        bottomNavigationView = activity?.findViewById(R.id.bottom_nav_main) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavListener.getBottomNavigationListenerFragment(this))

        if (binding.dboxWarningBtn.isVisible) {
            binding.dboxWarningBtn.setOnClickListener {
                bottomNavigationView.selectedItemId = R.id.nav_tab_search
            }
        }
        binding.dboxWarningBtn
    }

}