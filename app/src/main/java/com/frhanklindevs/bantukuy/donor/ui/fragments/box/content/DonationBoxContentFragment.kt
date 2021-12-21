package com.frhanklindevs.bantukuy.donor.ui.fragments.box.content

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonationBoxContentBinding
import com.frhanklindevs.bantukuy.donor.data.box.DonationCashItems
import com.frhanklindevs.bantukuy.donor.data.box.DonationGoodsItems
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity
import com.frhanklindevs.bantukuy.utils.PopUpCreator
import com.frhanklindevs.bantukuy.utils.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class DonationBoxContentFragment : Fragment(), DonorMoneyAdapter.OnItemClickCallback, DonorGoodsAdapter.OnItemClickCallback {

    private var _binding : FragmentDonationBoxContentBinding? = null
    private val binding get() = _binding!!

    private var userId: Int = 0
    private lateinit var popupAddCash: Dialog
    private lateinit var popUpText: TextView
    private lateinit var cashSpinner: Spinner
    private lateinit var arrayAdapter: ArrayAdapter<CharSequence>
    private lateinit var editCashValue: TextInputEditText
    private lateinit var popUpAddCashBtnConfirm: AppCompatImageButton
    private lateinit var editCashTemp: DonationCashItems

    private lateinit var viewModel: DonationContentViewModel
    private lateinit var moneyAdapter: DonorMoneyAdapter
    private lateinit var goodsAdapter: DonorGoodsAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentDonationBoxContentBinding.inflate(inflater, container, false)
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
            setView()
            setViewModel()
            setViewBehaviors()
        }
    }

    private fun setView() {
        popupAddCash = PopUpCreator.createSmallAddCashPopUpDialog(requireActivity())
        popUpText = popupAddCash.findViewById(R.id.popup_text)
        cashSpinner = popupAddCash.findViewById(R.id.popup_cash_spinner)
        arrayAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.cash_categories, android.R.layout.simple_spinner_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cashSpinner.adapter = it
        }
        editCashValue = popupAddCash.findViewById(R.id.ed_cash_value)
        popUpAddCashBtnConfirm = popupAddCash.findViewById(R.id.btn_add_money_confirm)
    }

    private fun setViewBehaviors() {
        binding.btnReload.setOnClickListener {
            viewModel.setBox()
        }
        binding.btnAddMoney.setOnClickListener {
            editCashValue.setText("")
            popUpAddCashBtnConfirm.setOnClickListener(addCashAction)
            popUpAddCashBtnConfirm.setImageDrawable(resources.getDrawable(R.drawable.ic_add_circle_outline_70_white))
            cashSpinner.isEnabled = true
            popUpText.text = getString(R.string.add_cash)
            popupAddCash.show()
        }
    }

    private val addCashAction: View.OnClickListener = View.OnClickListener {
        if (cashSpinner.selectedItem.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Mohon pilih kategori yang tersedia", Toast.LENGTH_SHORT).show()
        } else {
            if (editCashValue.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Mohon masukan nominal donasi", Toast.LENGTH_SHORT).show()
            } else {
                val value = editCashValue.text.toString().toDouble()
                if (value <= 0) {
                    Toast.makeText(requireContext(), "Mohon masukan nominal yang valid", Toast.LENGTH_SHORT).show()
                } else {
                    val category = cashSpinner.selectedItem.toString()

                    viewModel.insertOrUpdateCash(category, value)
                    editCashValue.setText("")
                    popupAddCash.dismiss()
                }
            }
        }
    }

    private val editCashAction: View.OnClickListener = View.OnClickListener {
        if (cashSpinner.selectedItem.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Mohon pilih kategori yang tersedia", Toast.LENGTH_SHORT).show()
        } else {
            if (editCashValue.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Mohon masukan nominal donasi", Toast.LENGTH_SHORT).show()
            } else {
                val value = editCashValue.text.toString().toDouble()
                if (value <= 0) {
                    Toast.makeText(requireContext(), "Mohon masukan nominal yang valid", Toast.LENGTH_SHORT).show()
                } else {
                    val category = cashSpinner.selectedItem.toString()

                    val updatedCash = DonationCashItems(
                        editCashTemp.boxId,
                        editCashTemp.id,
                        category,
                        value
                    )

                    viewModel.updateCash(updatedCash)
                    editCashValue.setText("")
                    popupAddCash.dismiss()
                }
            }
        }
    }


    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[DonationContentViewModel::class.java]
        moneyAdapter = DonorMoneyAdapter()
        goodsAdapter = DonorGoodsAdapter()

        viewModel.listMoney.observe(viewLifecycleOwner, {
            if (it != null) {
                moneyAdapter.setListMoney(it)
                moneyAdapter.setOnItemClickCallback(this)
                binding.rvMoney.layoutManager = LinearLayoutManager(requireContext())
                binding.rvMoney.adapter = moneyAdapter
                binding.rvMoney.setHasFixedSize(true)
            }
        })
        viewModel.listGoods.observe(viewLifecycleOwner, {
            if (it != null) {
                goodsAdapter.setListGoods(it)
                goodsAdapter.setOnItemClickCallback(this)
                binding.rvGoods.layoutManager = LinearLayoutManager(requireContext())
                binding.rvGoods.adapter = goodsAdapter
                binding.rvGoods.setHasFixedSize(true)
            }
        })

        viewModel.setUserId(userId)
    }

    override fun onEditCashValueBtnClicked(cashItem: DonationCashItems) {
        editCashTemp = cashItem

        popUpAddCashBtnConfirm.setImageDrawable(resources.getDrawable(R.drawable.ic_pen))
        popUpAddCashBtnConfirm.setOnClickListener(editCashAction)
        popUpText.text = getString(R.string.edit_cash)

        val spinnerPosttion = arrayAdapter.getPosition(cashItem.cashName)
        cashSpinner.setSelection(spinnerPosttion)
        cashSpinner.isEnabled = false
        editCashValue.setText("${cashItem.cashValue}")

        popupAddCash.show()
    }

    override fun onEditGoodsBtnClicked(goodsItem: DonationGoodsItems) {
        //TODO: Implement Edit Button Behavior for Goods Item
    }

    override fun onDeleteGoodsBtnClicked(goodsItem: DonationGoodsItems) {
        //TODO: Implement Delete Button Behavior for Goods Item
    }


}