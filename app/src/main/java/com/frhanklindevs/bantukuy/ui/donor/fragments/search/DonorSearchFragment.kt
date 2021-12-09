package com.frhanklindevs.bantukuy.ui.donor.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.frhanklindevs.bantukuy.databinding.FragmentDonorSearchBinding
import com.frhanklindevs.bantukuy.ui.donor.data.PlaceItem
import com.frhanklindevs.bantukuy.ui.donor.utils.DummyData
import com.frhanklindevs.bantukuy.ui.donor.utils.ViewModelFactory

class DonorSearchFragment : Fragment(), DonorSearchAdapter.OnItemClickCallback {

    private var _binding : FragmentDonorSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DonorSearchViewModel
    private lateinit var searchAdapter: DonorSearchAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[DonorSearchViewModel::class.java]

            searchAdapter = DonorSearchAdapter(DummyData.getDummyLiveData().value!!)

            //Put this line inside observer
            searchAdapter.setOnItemClickCallback(this)

            binding.donorSearchRvSearch.layoutManager = GridLayoutManager(context, SPAN_COUNT)
            binding.donorSearchRvSearch.setHasFixedSize(true)
            binding.donorSearchRvSearch.adapter = searchAdapter
        }
    }

    override fun onItemClicked(home: PlaceItem) {
        //TODO Intent to show Place's Details
        Toast.makeText(requireActivity(), "You have selected data: ${home.name}", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val SPAN_COUNT = 2
    }

}