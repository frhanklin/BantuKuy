package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonorDashboardBinding

class DonorDashboardFragment : Fragment() {

    private var _binding : FragmentDonorDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDonorDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUserGreetings()
    }

    private fun setUserGreetings() {
        binding.donorDashTvGreetingsTitle.text = getString(R.string.donor_user_greetings, "username")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}