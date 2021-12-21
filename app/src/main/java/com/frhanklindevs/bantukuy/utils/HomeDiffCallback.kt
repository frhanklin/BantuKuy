package com.frhanklindevs.bantukuy.utils

import androidx.recyclerview.widget.DiffUtil
import com.frhanklindevs.bantukuy.donor.data.api.PlaceItem
import com.frhanklindevs.bantukuy.donor.data.box.DonationCashItems
import com.frhanklindevs.bantukuy.donor.data.box.DonationGoodsItems

class HomeDiffCallback(private val mOldHomesList: List<PlaceItem>, private val mNewHomesList: List<PlaceItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldHomesList.size

    override fun getNewListSize(): Int = mNewHomesList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldHomesList[oldItemPosition].placeId == mNewHomesList[newItemPosition].placeId

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldHomesList[oldItemPosition].name == mNewHomesList[newItemPosition].name

}

class MoneyDiffCallback(private val mOldMoneyList: List<DonationCashItems>, private val mNewMoneyList: List<DonationCashItems>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldMoneyList.size

    override fun getNewListSize(): Int = mNewMoneyList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldMoneyList[oldItemPosition].id == mNewMoneyList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldMoneyList[oldItemPosition].cashValue == mNewMoneyList[newItemPosition].cashValue

}

class GoodsDiffCallback(private val mOldGoodsList: List<DonationGoodsItems>, private val mNewGoodsList: List<DonationGoodsItems>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldGoodsList.size

    override fun getNewListSize(): Int = mNewGoodsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldGoodsList[oldItemPosition].id == mNewGoodsList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        mOldGoodsList[oldItemPosition].goodsWeight == mNewGoodsList[newItemPosition].goodsWeight

}