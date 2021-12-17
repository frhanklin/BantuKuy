package com.frhanklindevs.bantukuy.donor.ui.fragments.box.item.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.databinding.FragmentDonationBoxItemHomeBinding
import com.frhanklindevs.bantukuy.utils.ViewModelFactory

class DonationBoxItemHome : Fragment() {

    private var _binding : FragmentDonationBoxItemHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DonationBoxItemHomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentDonationBoxItemHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel()
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[DonationBoxItemHomeViewModel::class.java]


    }
}