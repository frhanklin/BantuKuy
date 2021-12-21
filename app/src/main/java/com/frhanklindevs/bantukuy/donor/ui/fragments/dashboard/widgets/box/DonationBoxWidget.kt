package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.box

import android.icu.text.NumberFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonationBoxWidgetBinding
import com.frhanklindevs.bantukuy.donor.data.model.SharedDonorViewModel
import com.frhanklindevs.bantukuy.donor.ui.bottomnav.BottomNavListener
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity
import com.frhanklindevs.bantukuy.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class DonationBoxWidget : Fragment() {

    private var _binding : FragmentDonationBoxWidgetBinding? = null
    private val binding get() = _binding!!

    private var userId: Int = 0
    private lateinit var viewModel: DonationBoxWidgetViewModel
    private val sharedViewModel: SharedDonorViewModel by activityViewModels()

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

        userId = requireActivity().intent.getIntExtra(DonorHomeActivity.EXTRA_USER_ID, 0)
        setViewModel()
//        setSharedViewModel()
        setViewBehaviors()
    }

    private fun setSharedViewModel() {
        sharedViewModel.isDonateable.observe(viewLifecycleOwner, {
            binding.donationBoxWarning.visibility = if (it) View.GONE else View.VISIBLE
            binding.donationBoxContents.visibility = if (it) View.VISIBLE else View.GONE
        })

        sharedViewModel.homeDetails.observe(viewLifecycleOwner, {
            if (!sharedViewModel.homeDetails.value?.name.isNullOrEmpty()) {
                binding.boxContentHouseTargetText.text = sharedViewModel.homeDetails.value?.name
            }
        })

        sharedViewModel.currentTotalDonationMoney.observe(viewLifecycleOwner, {
            binding.boxDonationDetailCashText.text = convertToRupiah(it)
        })
        sharedViewModel.currentDonationWeight.observe(viewLifecycleOwner, {
            binding.boxDonationDetailGoodsText.text = String.format(getString(R.string.format_kilogram), it.toString())
        })
        sharedViewModel.currentTotalExpeditionFee.observe(viewLifecycleOwner, {
            binding.boxDonationDetailExpeditionText.text = convertToRupiah(it)
        })
        sharedViewModel.currentTotalCost.observe(viewLifecycleOwner, {
            binding.boxDonationTotalPaymentText.text = convertToRupiah(it)
        })

        sharedViewModel.setUserId(userId)
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[DonationBoxWidgetViewModel::class.java]

        viewModel.isDonateable.observe(viewLifecycleOwner, {
            binding.donationBoxWarning.visibility = if (it) View.GONE else View.VISIBLE
            binding.donationBoxContents.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.homeName.observe(viewLifecycleOwner, {
            binding.boxContentHouseTargetText.text = it
        })
        viewModel.currentTotalDonationMoney.observe(viewLifecycleOwner, {
            binding.boxDonationDetailCashText.text = convertToRupiah(it)
        })
        viewModel.currentTotalDonationGoodsWeight.observe(viewLifecycleOwner, {
            binding.boxDonationDetailGoodsText.text = String.format(getString(R.string.format_kilogram), it.toString())
        })
        viewModel.currentTotalExpeditionFee.observe(viewLifecycleOwner, {
            binding.boxDonationDetailExpeditionText.text = convertToRupiah(it)
        })
        viewModel.currentTotalCost.observe(viewLifecycleOwner, {
            binding.boxDonationTotalPaymentText.text = convertToRupiah(it)
        })

        viewModel.setUserId(userId)
    }

    private fun convertToRupiah(value: Int?): CharSequence {
        return if (value != null) {
            val valueString = NumberFormat.getNumberInstance(Locale.US).format(value)
            String.format(getString(R.string.format_rupiah), valueString)
        } else {
            val valueString = 0
            String.format(getString(R.string.format_rupiah), valueString)
        }
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

        binding.donationBoxBase.setOnClickListener {
            viewModel.setBox()
        }

    }


}