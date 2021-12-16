package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SearchView
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentBantuCariWidgetBinding
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class BantuCariWidget : Fragment() {

    private var _binding : FragmentBantuCariWidgetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBantuCariWidgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewBehaviors()
    }

    private fun setViewBehaviors() {
        val bottomNavigationView = activity?.findViewById(R.id.bottom_nav_main) as BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(bottomNavListener)
        var text: String

        binding.bantuCariQuickNear.setOnClickListener{
            text = "panti jakarta terdekat"
            val bantuSearchView = activity?.findViewById(R.id.donor_search_sv_home) as SearchView
            bantuSearchView.setQuery(text, true)

            bottomNavigationView.selectedItemId = R.id.nav_tab_search
        }

        binding.bantuCariQuickOrphanage.setOnClickListener{
            text = "panti asuhan di jakarta"
            val bantuSearchView = activity?.findViewById(R.id.donor_search_sv_home) as SearchView
            bantuSearchView.setQuery(text, true)

            bottomNavigationView.selectedItemId = R.id.nav_tab_search
        }

        binding.bantuCariQuickNursing.setOnClickListener {
            text = "panti jompo di jakarta"
            val bantuSearchView = activity?.findViewById(R.id.donor_search_sv_home) as SearchView
            bantuSearchView.setQuery(text, true)

            bottomNavigationView.selectedItemId = R.id.nav_tab_search
        }

        binding.bantuCariSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                text = query.lowercase(Locale.getDefault())
                if (!text.contains("panti", true)) {
                    text = "panti $text"
                }
                if (!text.contains("jakarta", true)) {
                    text = "$text jakarta"
                }

                val bantuSearchView = activity?.findViewById(R.id.donor_search_sv_home) as SearchView
                bantuSearchView.setQuery(text, true)

                bottomNavigationView.selectedItemId = R.id.nav_tab_search

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })

        binding.bantuCariButton.setOnClickListener {
            text = binding.bantuCariSearch.query.toString()
            val bantuSearchView = activity?.findViewById(R.id.donor_search_sv_home) as SearchView
            val bantuSearchButton = activity?.findViewById(R.id.donor_search_btn_sv_home) as AppCompatButton

            bantuSearchView.setQuery(text, false)
            bantuSearchButton.performClick()

            bottomNavigationView.selectedItemId = R.id.nav_tab_search
        }

        binding.bantuCariTvSubtitle.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.nav_tab_search
        }
    }

    private val bottomNavListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_tab_dashboard -> {
                    activity?.supportFragmentManager?.beginTransaction()?.hide(DonorHomeActivity.active)?.show(
                        DonorHomeActivity.fragment1
                    )?.commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment1
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_tab_search -> {
                    activity?.supportFragmentManager?.beginTransaction()?.hide(DonorHomeActivity.active)?.show(
                        DonorHomeActivity.fragment2
                    )?.commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment2
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_tab_donation_box -> {
                    activity?.supportFragmentManager?.beginTransaction()?.hide(DonorHomeActivity.active)?.show(
                        DonorHomeActivity.fragment3
                    )?.commit()
                    DonorHomeActivity.active = DonorHomeActivity.fragment3
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

}