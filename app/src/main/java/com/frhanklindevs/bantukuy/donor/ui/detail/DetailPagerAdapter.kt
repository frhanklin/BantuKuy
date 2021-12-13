package com.frhanklindevs.bantukuy.donor.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

// Reference code converted from Java [CodingWithMitch.com]
class DetailPagerAdapter(fm: FragmentManager, fragments: ArrayList<Fragment>) :
    FragmentStatePagerAdapter(fm) {

    private var mFragments: ArrayList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    init {
        mFragments = fragments
    }
}