package com.frhanklindevs.bantukuy.donor.ui.fragments.box.content

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
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
import okhttp3.internal.format
import kotlin.math.roundToInt

class DonationBoxContentFragment : Fragment(), DonorMoneyAdapter.OnItemClickCallback, DonorGoodsAdapter.OnItemClickCallback {

    private var _binding : FragmentDonationBoxContentBinding? = null
    private val binding get() = _binding!!

    private var userId: Int = 0
    private lateinit var popUpAddEdit: Dialog
    private lateinit var popUpDelete: Dialog

    private lateinit var popUpImg: ImageView

    private lateinit var popUpText: TextView
    private lateinit var popUpDelText: TextView

    private lateinit var popUpSpinner: Spinner
    private lateinit var cashArrayAdapter: ArrayAdapter<CharSequence>
    private lateinit var goodsArrayAdapter: ArrayAdapter<CharSequence>
    private lateinit var popUpEditValue: TextInputEditText

    private lateinit var popUpAddEditBtnConfirm: AppCompatImageButton
    private lateinit var popUpDeleteBtnConfirm: AppCompatImageButton
    private lateinit var popUpDeleteBtnCancel: AppCompatImageButton

    private lateinit var editCashTemp: DonationCashItems
    private lateinit var editGoodsTemp: DonationGoodsItems

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
        popUpAddEdit = PopUpCreator.createSmallAddPopUpDialog(requireActivity())
        popUpDelete = PopUpCreator.createSmallDelPopUpDialog(requireActivity())

        popUpImg = popUpAddEdit.findViewById(R.id.popup_img)

        popUpText = popUpAddEdit.findViewById(R.id.popup_text)
        popUpDelText = popUpDelete.findViewById(R.id.popup_del_detail_text)

        popUpSpinner = popUpAddEdit.findViewById(R.id.popup_spinner)
        cashArrayAdapter =
            ArrayAdapter.createFromResource(
                requireContext(), R.array.spinner_cash_categories,
                android.R.layout.simple_spinner_item
            ).also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        goodsArrayAdapter =
            ArrayAdapter.createFromResource(
                requireContext(), R.array.spinner_goods_categories,
                android.R.layout.simple_spinner_item
            ).also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                popUpSpinner.adapter = it
            }
        popUpEditValue = popUpAddEdit.findViewById(R.id.ed_cash_value)

        popUpAddEditBtnConfirm = popUpAddEdit.findViewById(R.id.btn_add_confirm)
        popUpDeleteBtnConfirm = popUpDelete.findViewById(R.id.btn_del_confirm)
        popUpDeleteBtnCancel = popUpDelete.findViewById(R.id.btn_del_cancel)
    }

    private fun setViewBehaviors() {
        binding.btnReload.setOnClickListener {
            viewModel.setBox()
        }
        binding.btnAddMoney.setOnClickListener {
            popUpSpinner.adapter = cashArrayAdapter

            popUpImg.setImageDrawable(context?.let { ctx ->
                AppCompatResources.getDrawable(ctx, R.drawable.ic_baseline_attach_money_80_white)
            })
            popUpEditValue.setText("")
            popUpEditValue.setHint(getString(R.string.cash_hint))
            popUpText.text = getString(R.string.add_cash)

            popUpAddEditBtnConfirm.setOnClickListener(addCashAction)
            popUpAddEditBtnConfirm.setImageDrawable(context?.let { ctx ->
                AppCompatResources.getDrawable(ctx, R.drawable.ic_add_circle_outline_70_white)
            })
            popUpSpinner.isEnabled = true
            popUpAddEdit.show()
        }
        binding.btnAddGoods.setOnClickListener {
            popUpSpinner.adapter = goodsArrayAdapter

            popUpImg.setImageDrawable(context?.let { ctx ->
                AppCompatResources.getDrawable(ctx, R.drawable.ic_box_open_solid)
            })
            popUpEditValue.setText("")
            popUpEditValue.setHint(getString(R.string.weight))
            popUpText.text = getString(R.string.add_goods)

            popUpAddEditBtnConfirm.setOnClickListener(addGoodsAction)
            popUpAddEditBtnConfirm.setImageDrawable(context?.let { ctx ->
                AppCompatResources.getDrawable(ctx, R.drawable.ic_add_circle_outline_70_white)
            })

            popUpSpinner.isEnabled = true
            popUpAddEdit.show()
        }
        popUpDeleteBtnConfirm.setOnClickListener {
            viewModel.deleteGoods(editGoodsTemp)
            popUpDelete.dismiss()
        }
        popUpDeleteBtnCancel.setOnClickListener {
            popUpDelText.text = ""
            popUpDelete.dismiss()
        }
    }

    private val addCashAction: View.OnClickListener = View.OnClickListener {
        if (popUpSpinner.selectedItem.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Mohon pilih kategori yang tersedia", Toast.LENGTH_SHORT).show()
        } else {
            if (popUpEditValue.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Mohon masukan nominal donasi", Toast.LENGTH_SHORT).show()
            } else {
                val value = popUpEditValue.text.toString().toDouble()
                if (value <= 0) {
                    Toast.makeText(requireContext(), "Mohon masukan nominal yang valid", Toast.LENGTH_SHORT).show()
                } else {
                    val category = popUpSpinner.selectedItem.toString()

                    viewModel.insertOrUpdateCash(category, value)
                    popUpEditValue.setText("")
                    popUpAddEdit.dismiss()
                }
            }
        }
    }

    private val editCashAction: View.OnClickListener = View.OnClickListener {
        if (popUpSpinner.selectedItem.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Mohon pilih kategori yang tersedia", Toast.LENGTH_SHORT).show()
        } else {
            if (popUpEditValue.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Mohon masukan nominal donasi", Toast.LENGTH_SHORT).show()
            } else {
                val value = popUpEditValue.text.toString().toDouble()
                if (value <= 0) {
                    Toast.makeText(requireContext(), "Mohon masukan nominal yang valid", Toast.LENGTH_SHORT).show()
                } else {
                    val category = popUpSpinner.selectedItem.toString()

                    val updatedCash = DonationCashItems(
                        editCashTemp.boxId,
                        editCashTemp.id,
                        category,
                        value
                    )

                    viewModel.updateCash(updatedCash)
                    popUpEditValue.setText("")
                    popUpAddEdit.dismiss()
                }
            }
        }
    }

    private val addGoodsAction: View.OnClickListener = View.OnClickListener {
        if (popUpSpinner.selectedItem.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Mohon pilih kategori yang tersedia", Toast.LENGTH_SHORT).show()
        } else {
            if (popUpEditValue.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Mohon masukan bobot barang", Toast.LENGTH_SHORT).show()
            } else {
                val value = popUpEditValue.text.toString().toInt()
                if (value <= 0) {
                    Toast.makeText(requireContext(), "Mohon masukan bobot yang valid", Toast.LENGTH_SHORT).show()
                } else {
                    val category = popUpSpinner.selectedItem.toString()

                    viewModel.insertOrUpdateGoods(category, value)
                    popUpEditValue.setText("")
                    popUpAddEdit.dismiss()

                }
            }
        }
    }

    private val editGoodsAction: View.OnClickListener = View.OnClickListener {
        if (popUpSpinner.selectedItem.toString().isEmpty()) {
            Toast.makeText(requireContext(), "Mohon pilih kategori yang tersedia", Toast.LENGTH_SHORT).show()
        } else {
            if (popUpEditValue.text.toString().isEmpty()) {
                Toast.makeText(requireContext(), "Mohon masukan bobot donasi", Toast.LENGTH_SHORT).show()
            } else {
                val value = popUpEditValue.text.toString().toDouble().roundToInt()
                if (value <= 0) {
                    Toast.makeText(requireContext(), "Mohon masukan bobot yang valid", Toast.LENGTH_SHORT).show()
                } else {
                    val category = popUpSpinner.selectedItem.toString()

                    val updatedGoods = DonationGoodsItems(
                        editGoodsTemp.boxId,
                        editGoodsTemp.id,
                        category,
                        value
                    )

                    viewModel.updateGoods(updatedGoods)
                    popUpEditValue.setText("")
                    popUpAddEdit.dismiss()
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
                binding.rvMoney.isNestedScrollingEnabled = true
            }
        })
        viewModel.listGoods.observe(viewLifecycleOwner, {
            if (it != null) {
                println("Goods item counte: ${it.size}")
                goodsAdapter.setListGoods(it)
                goodsAdapter.setOnItemClickCallback(this)
                binding.rvGoods.layoutManager = LinearLayoutManager(activity)
                binding.rvGoods.adapter = goodsAdapter
                binding.rvGoods.setHasFixedSize(true)
                binding.rvGoods.isNestedScrollingEnabled = true
            }
        })

        viewModel.setUserId(userId)
    }

    override fun onEditCashValueBtnClicked(cashItem: DonationCashItems) {
        popUpSpinner.adapter = cashArrayAdapter
        editCashTemp = cashItem

        popUpImg.setImageDrawable(context?.let { ctx ->
            AppCompatResources.getDrawable(ctx, R.drawable.ic_baseline_attach_money_80_white)
        })
        popUpAddEditBtnConfirm.setImageDrawable(context?.let { ctx ->
            AppCompatResources.getDrawable(ctx, R.drawable.ic_pen)
        })
        popUpAddEditBtnConfirm.setOnClickListener(editCashAction)
        popUpText.text = getString(R.string.edit_cash)

        val spinnerPosttion = cashArrayAdapter.getPosition(cashItem.cashName)
        popUpSpinner.setSelection(spinnerPosttion)
        popUpSpinner.isEnabled = false
        popUpEditValue.setText("${cashItem.cashValue}")
        popUpEditValue.setHint(getString(R.string.cash_hint))

        popUpAddEdit.show()
    }

    override fun onEditGoodsBtnClicked(goodsItem: DonationGoodsItems) {
        //TODO: Implement Edit Button Behavior for Goods Item
        popUpSpinner.adapter = goodsArrayAdapter
        editGoodsTemp = goodsItem

        popUpImg.setImageDrawable(context?.let { ctx ->
            AppCompatResources.getDrawable(ctx, R.drawable.ic_box_open_solid)
        })
        popUpAddEditBtnConfirm.setImageDrawable(context?.let { ctx ->
            AppCompatResources.getDrawable(ctx, R.drawable.ic_pen)
        })
        popUpAddEditBtnConfirm.setOnClickListener(editGoodsAction)
        popUpText.text = getString(R.string.edit_goods)

        val spinnerPosttion = goodsArrayAdapter.getPosition(goodsItem.goodsName)
        popUpSpinner.setSelection(spinnerPosttion)
        popUpSpinner.isEnabled = false
        popUpEditValue.setText(goodsItem.goodsWeight.toString())
        popUpEditValue.setText("${editGoodsTemp.goodsWeight}")
        popUpEditValue.setHint(getString(R.string.weight))

        popUpAddEdit.show()
    }

    override fun onDeleteGoodsBtnClicked(goodsItem: DonationGoodsItems) {
        //TODO: Implement Delete Button Behavior for Goods Item
        editGoodsTemp = goodsItem
        val textPlaceholder = getString(R.string.popup_del_goods_placeholder)

        popUpDelText.text = format(textPlaceholder, goodsItem.goodsName, goodsItem.goodsWeight)
        popUpDelete.show()
    }


}