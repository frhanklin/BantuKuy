package com.frhanklindevs.bantukuy.donor.ui.fragments.box.content

import android.icu.text.NumberFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonationBoxItemMoneyBinding
import com.frhanklindevs.bantukuy.donor.data.box.DonationCashItems
import com.frhanklindevs.bantukuy.utils.MoneyDiffCallback
import java.util.*
import kotlin.collections.ArrayList

class DonorMoneyAdapter: RecyclerView.Adapter<DonorMoneyAdapter.MoneyViewHolder>() {

    private val listMoneyDonation = ArrayList<DonationCashItems>()
    fun setListMoney(listMoneyDonation: List<DonationCashItems>) {
        val diffCallback = MoneyDiffCallback(this.listMoneyDonation, listMoneyDonation)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listMoneyDonation.clear()
        this.listMoneyDonation.addAll(listMoneyDonation)
        diffResult.dispatchUpdatesTo(this)
    }

    class MoneyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = FragmentDonationBoxItemMoneyBinding.bind(itemView)
    }
    interface OnItemClickCallback {
        fun onEditCashValueBtnClicked(cashItem: DonationCashItems)
        fun onDeleteCashValueBtnClicked(cashItem: DonationCashItems)
    }
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_donation_box_item_money, parent, false)
        return MoneyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoneyViewHolder, position: Int) {
        val item = listMoneyDonation[position]
        with(holder) {
            binding.dboxBoxMoneyName.text = item.cashName
            binding.dboxBoxMoneyValue.text = convertToRupiah(item.cashValue.toInt())
            binding.dboxBoxMoneyBtnEdit.setOnClickListener {
                onItemClickCallback.onEditCashValueBtnClicked(item)
            }
            binding.dboxBoxMoneyBtnDel.setOnClickListener {
                onItemClickCallback.onDeleteCashValueBtnClicked(item)
            }
        }
    }

    override fun getItemCount(): Int = listMoneyDonation.size

    private fun convertToRupiah(value: Int?): CharSequence {
        return if (value != null) {
            val valueString = NumberFormat.getNumberInstance(Locale.US).format(value)
            String.format(rupiah_format, valueString)
        } else {
            val valueString = 0
            String.format(rupiah_format, valueString)
        }
    }

    companion object {
        private const val rupiah_format = "Rp %s"
    }
}