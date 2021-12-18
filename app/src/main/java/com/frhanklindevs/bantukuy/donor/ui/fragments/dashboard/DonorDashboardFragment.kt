package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonorDashboardBinding
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity
import com.frhanklindevs.bantukuy.utils.ViewModelFactory

class DonorDashboardFragment : Fragment() {

    private var _binding : FragmentDonorDashboardBinding? = null
    private val binding get() = _binding!!

    private var userId: Int = 0
    private lateinit var viewModel: DonorDashboardViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentDonorDashboardBinding.inflate(inflater, container, false)
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
        viewModel = ViewModelProvider(this, factory)[DonorDashboardViewModel::class.java]
        
        viewModel.setUserId(userId)
        viewModel.username.observe(viewLifecycleOwner, { username ->
            binding.donorDashTvGreetingsTitle.text = getString(R.string.donor_user_greetings, username)
        })
    }
}