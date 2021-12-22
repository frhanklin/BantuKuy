package com.frhanklindevs.bantukuy.donor.ui.fragments.box.item.expedition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonationBoxItemExpeditionBinding
import com.frhanklindevs.bantukuy.donor.ui.fragments.box.content.DonationContentViewModel
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity
import com.frhanklindevs.bantukuy.utils.ViewModelFactory

class DonationBoxItemExpedition : Fragment() {

    private var userId: Int = 0
    private lateinit var viewModel: DonationBoxItemExpeditionViewModel


    private var _binding: FragmentDonationBoxItemExpeditionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentDonationBoxItemExpeditionBinding.inflate(inflater, container, false)
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
    }


    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[DonationBoxItemExpeditionViewModel::class.java]

        viewModel.expeditionDetail.observe(viewLifecycleOwner, {
            binding.dboxExpeditionName.text = it.expeditionCompany
            binding.dboxExpeditionValue.text = it.planName
        })
        viewModel.setUserId(userId)
    }

}