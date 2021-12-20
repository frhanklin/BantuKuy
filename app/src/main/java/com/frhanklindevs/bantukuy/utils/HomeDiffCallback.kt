package com.frhanklindevs.bantukuy.utils

import androidx.recyclerview.widget.DiffUtil
import com.frhanklindevs.bantukuy.donor.data.api.PlaceItem

class HomeDiffCallback(private val mOldHomesList: List<PlaceItem>, private val mNewHomesList: List<PlaceItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldHomesList.size

    override fun getNewListSize(): Int = mNewHomesList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldHomesList[oldItemPosition].placeId == mNewHomesList[newItemPosition].placeId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldHomesList[oldItemPosition].name == mNewHomesList[newItemPosition].name

}