package com.frhanklindevs.bantukuy.donor.ui.fragments.box.content

import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonationBoxItemGoodsBinding
import com.frhanklindevs.bantukuy.donor.data.box.DonationGoodsItems
import com.frhanklindevs.bantukuy.utils.GoodsDiffCallback
import com.google.android.material.progressindicator.BaseProgressIndicator
import java.util.*
import kotlin.collections.ArrayList

class DonorGoodsAdapter: RecyclerView.Adapter<DonorGoodsAdapter.GoodsViewHolder>() {

    private val listGoodsDonation = ArrayList<DonationGoodsItems>()
    fun setListGoods(listGoodsDonation: List<DonationGoodsItems>) {
        val diffCallback = GoodsDiffCallback(this.listGoodsDonation, listGoodsDonation)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listGoodsDonation.clear()
        this.listGoodsDonation.addAll(listGoodsDonation)
        diffResult.dispatchUpdatesTo(this)
    }

    class GoodsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = FragmentDonationBoxItemGoodsBinding.bind(itemView)
    }
    interface OnItemClickCallback {
        fun onEditGoodsBtnClicked(goodsItem: DonationGoodsItems)
        fun onDeleteGoodsBtnClicked(goodsItem: DonationGoodsItems)
    }
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_donation_box_item_goods, parent, false)
        return GoodsViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        val item = listGoodsDonation[position]
        with(holder) {
            binding.dboxGoodsName.text = item.goodsName
            binding.dboxGoodsDetail.text = convertToKilogram(item.goodsWeight)

        }
    }

    override fun getItemCount(): Int = listGoodsDonation.size

    private fun convertToKilogram(value: Int?): CharSequence {
        return if (value != null) {
            val valueString = NumberFormat.getNumberInstance(Locale.US).format(value)
            String.format(kilogram_format, valueString)
        } else {
            val valueString = 0
            String.format(kilogram_format, valueString)
        }
    }

    companion object {
        private const val kilogram_format = "%s kg"
    }
}