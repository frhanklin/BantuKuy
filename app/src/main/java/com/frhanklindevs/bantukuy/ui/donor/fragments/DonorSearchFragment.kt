package com.frhanklindevs.bantukuy.ui.donor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonorSearchBinding
import com.frhanklindevs.bantukuy.ui.donor.adapters.SearchHomesAdapter
import com.frhanklindevs.bantukuy.ui.donor.data.PlaceItem

class DonorSearchFragment : Fragment(), SearchHomesAdapter.OnItemClickCallback {

    private var _binding : FragmentDonorSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentDonorSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(home: PlaceItem) {
        //TODO Intent to show Place's Details
    }

}