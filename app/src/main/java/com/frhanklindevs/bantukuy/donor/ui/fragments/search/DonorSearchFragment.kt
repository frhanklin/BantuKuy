package com.frhanklindevs.bantukuy.donor.ui.fragments.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.frhanklindevs.bantukuy.databinding.FragmentDonorSearchBinding
import com.frhanklindevs.bantukuy.donor.data.PlaceItem
import com.frhanklindevs.bantukuy.donor.ui.detail.DetailSearchActivity
import com.frhanklindevs.bantukuy.utils.DummyData
import com.frhanklindevs.bantukuy.utils.ViewModelFactory
import java.util.*

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

            viewModel.isLoading.observe(viewLifecycleOwner, loadingStateObserver)
            viewModel.warningText.observe(viewLifecycleOwner, warningObserver)
            viewModel.recyclerState.observe(viewLifecycleOwner, recyclerObserver)

            viewModel.homesResults.observe(viewLifecycleOwner, homesObserver)
            viewModel.queryText.observe(viewLifecycleOwner, queryObserver)
            viewModel.setQuery("")

            //Checkpoint mark
            searchAdapter = DonorSearchAdapter()

            binding.donorSearchRvSearch.layoutManager = GridLayoutManager(context, SPAN_COUNT)
            binding.donorSearchRvSearch.setHasFixedSize(true)
            binding.donorSearchRvSearch.adapter = searchAdapter
        }

        setViewBehaviors()

    }

    private fun setViewBehaviors() {



        binding.donorSearchSvHome.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                var text = query.lowercase(Locale.getDefault())
                if (!text.contains("panti", true)) {
                    text = "panti $text"
                }
                if (!text.contains("jakarta", true)) {
                    text = "$text jakarta"
                }

                viewModel.setQuery(text)

                return true
            }

            override fun onQueryTextChange(text: String): Boolean {
                return false
            }
        })
        binding.donorSearchBtnSvHome.setOnClickListener {
            var text = binding.donorSearchSvHome.query.toString()
            if (!text.contains("panti", true)) {
                text = "panti $text"
            }
            if (!text.contains("jakarta", true)) {
                text = "$text jakarta"
            }

            viewModel.setQuery(text)
        }
    }

    private val homesObserver = Observer<List<PlaceItem>> {
        if (it != null) {
            searchAdapter.setListHomes(it)
            searchAdapter.setOnItemClickCallback(this)
        }
    }

    private val loadingStateObserver = Observer<Boolean> {
        binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
    }

    private val warningObserver = Observer<String> {
        binding.warningText.visibility = if (it == "") View.GONE else View.VISIBLE
        binding.warningText.text = it
    }

    private val recyclerObserver = Observer<Boolean> {
        binding.donorSearchRvSearch.visibility = if (it) View.VISIBLE else View.GONE
    }

    private val queryObserver = Observer<String> {
        viewModel.searchHomes()
    }


    override fun onItemClicked(home: PlaceItem) {
        //TODO Intent to show Place's Details
        Toast.makeText(requireActivity(), "You have selected data: ${home.name}", Toast.LENGTH_SHORT).show()
        val intent = Intent(requireActivity(), DetailSearchActivity::class.java)
        intent.putExtra(DetailSearchActivity.EXTRA_PLACE, home.placeId)
        startActivity(intent)
    }

    companion object {
        private const val SPAN_COUNT = 2
    }

}