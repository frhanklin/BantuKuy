package com.frhanklindevs.bantukuy.donor.ui.fragments.box.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonationBoxItemHomeBinding

class DonationBoxItemHome : Fragment() {

    private var _binding : FragmentDonationBoxItemHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDonationBoxItemHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}