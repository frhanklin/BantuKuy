package com.frhanklindevs.bantukuy.donor.ui.fragments.box.item.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonationBoxItemHomeBinding
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity
import com.frhanklindevs.bantukuy.utils.ViewModelFactory

class DonationBoxItemHome : Fragment() {

    private var _binding : FragmentDonationBoxItemHomeBinding? = null
    private val binding get() = _binding!!

    private var userId: Int = 0
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

        if (activity != null) {
            userId = requireActivity().intent.getIntExtra(DonorHomeActivity.EXTRA_USER_ID, 0)
            setViewModel()
            setViewBehavior()
        }
    }

    private fun setViewBehavior() {
        binding.btnReload.setOnClickListener {
            viewModel.setBox()
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[DonationBoxItemHomeViewModel::class.java]

        viewModel.homeName.observe(viewLifecycleOwner, {
            binding.dboxHomeName.text = it
        })
        viewModel.homeAddress.observe(viewLifecycleOwner, {
            binding.dboxHomeAddress.text = it
        })
        viewModel.homeImageUrl.observe(viewLifecycleOwner, {
            Glide.with(this)
                .load(it)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(binding.dboxHomeImg)
        })


        viewModel.setUserId(userId)
    }
}