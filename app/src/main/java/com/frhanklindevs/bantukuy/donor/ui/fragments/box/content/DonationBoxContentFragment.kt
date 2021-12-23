package com.frhanklindevs.bantukuy.donor.ui.fragments.box.content

import android.app.Dialog
import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentDonationBoxContentBinding
import com.frhanklindevs.bantukuy.donor.data.box.DonationCashItems
import com.frhanklindevs.bantukuy.donor.data.box.DonationGoodsItems
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity
import com.frhanklindevs.bantukuy.utils.PopUpCreator
import com.frhanklindevs.bantukuy.utils.ViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.delay
import okhttp3.internal.format
import java.util.*
import kotlin.math.roundToInt

class DonationBoxContentFragment : Fragment(), DonorMoneyAdapter.OnItemClickCallback, DonorGoodsAdapter.OnItemClickCallback {

    private var _binding : FragmentDonationBoxContentBinding? = null
    private val binding get() = _binding!!

    private var userId: Int = 0

    private lateinit var popUpAddEdit: Dialog
    private lateinit var popUpDelete: Dialog
    private lateinit var popUpPay:Dialog
    private lateinit var popUpImg: ImageView

    private lateinit var popUpText: TextView
    private lateinit var popUpDelText: TextView
    private lateinit var popUpPayText: TextView

    private lateinit var popUpSpinner: Spinner
    private lateinit var cashArrayAdapter: ArrayAdapter<CharSequence>
    private lateinit var goodsArrayAdapter: ArrayAdapter<CharSequence>
    private lateinit var popUpEditValue: TextInputEditText

    private lateinit var popUpAddEditBtnConfirm: AppCompatImageButton
    private lateinit var popUpDeleteBtnConfirm: AppCompatImageButton
    private lateinit var popUpDeleteBtnCancel: AppCompatImageButton
    private lateinit var popUpPayBtnConfirm: AppCompatImageButton
    private lateinit var popUpPayBtnCancel: AppCompatImageButton

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
        popUpPay = PopUpCreator.paymentConfirmation(requireActivity())

        popUpImg = popUpAddEdit.findViewById(R.id.popup_img)

        popUpText = popUpAddEdit.findViewById(R.id.popup_text)
        popUpDelText = popUpDelete.findViewById(R.id.popup_del_detail_text)
        popUpPayText = popUpPay.findViewById(R.id.popup_pay_detail_text)

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
        popUpPayBtnConfirm = popUpPay.findViewById(R.id.btn_pay_confirm)
        popUpPayBtnCancel = popUpPay.findViewById(R.id.btn_pay_cancel)
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
            popUpEditValue.hint = getString(R.string.cash_hint)
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
            popUpEditValue.hint = getString(R.string.weight)
            popUpText.text = getString(R.string.add_goods)

            popUpAddEditBtnConfirm.setOnClickListener(addGoodsAction)
            popUpAddEditBtnConfirm.setImageDrawable(context?.let { ctx ->
                AppCompatResources.getDrawable(ctx, R.drawable.ic_add_circle_outline_70_white)
            })

            popUpSpinner.isEnabled = true
            popUpAddEdit.show()
        }

        popUpDeleteBtnCancel.setOnClickListener {
            popUpDelText.text = ""
            popUpDelete.dismiss()
        }

        popUpPayBtnConfirm.setOnClickListener{
            val handler = Handler(Looper.getMainLooper())
            popUpPayBtnConfirm.visibility = View.GONE
            popUpPayBtnCancel.visibility = View.GONE
            popUpPayText.text = "Memroses..."
            handler.postDelayed({
                popUpPayText.text = "Selesai"
                viewModel.updateBoxCompleted()
                popUpPay.dismiss()


            }, 1000)


        }

        popUpPayBtnCancel.setOnClickListener{
            popUpPay.dismiss()
        }

        binding.donationBoxBtnDonate.setOnClickListener{
            popUpPayBtnConfirm.visibility = View.VISIBLE
            popUpPayBtnCancel.visibility = View.VISIBLE

            popUpPay.show()
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

        viewModel.homeName.observe(viewLifecycleOwner, {
            binding.dboxHomeName.text = it
        })
        viewModel.homeAddress.observe(viewLifecycleOwner, {
            binding.dboxHomeAddress.text = it
        })
        viewModel.homeImageUrl.observe(viewLifecycleOwner, {
            Glide.with(this)
                .load(it)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(binding.dboxHomeImg)
        })

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

        viewModel.expeditionDetail.observe(viewLifecycleOwner, {
            binding.dboxExpeditionName.text = it.expeditionCompany
            binding.dboxExpeditionValue.text = it.planName
        })

        viewModel.currentTotalDonationMoney.observe(viewLifecycleOwner, {
            binding.dboxOverviewCashValue.text = convertCurrency(it)
        })
        viewModel.currentTotalDonationGoodsWeight.observe(viewLifecycleOwner, {
            binding.dboxOverviewGoodsValue.text = String.format(getString(R.string.format_kilogram), it.toString())
        })
        viewModel.currentTotalExpeditionFee.observe(viewLifecycleOwner, {
            binding.dboxOverviewExpeditionValue.text = convertCurrency(it)
        })
        viewModel.currentTotalCost.observe(viewLifecycleOwner, {
            binding.dboxOverviewTotalValue.text = convertCurrency(it)
        })
        viewModel.isDonateable.observe(viewLifecycleOwner, {
            binding.donationBoxBtnDonate.visibility = if (it) View.VISIBLE else View.GONE
        })



        viewModel.setUserId(userId)
    }

    private fun convertCurrency(value: Int?): CharSequence {
        return if (value != null) {
            val valueString = NumberFormat.getNumberInstance(Locale.US).format(value)
            String.format(getString(R.string.format_rupiah), valueString)
        } else {
            val valueString = 0
            String.format(getString(R.string.format_rupiah), valueString)
        }
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
        popUpEditValue.hint = getString(R.string.cash_hint)

        popUpAddEdit.show()
    }

    override fun onEditGoodsBtnClicked(goodsItem: DonationGoodsItems) {
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
        popUpEditValue.hint = getString(R.string.weight)

        popUpAddEdit.show()
    }

    override fun onDeleteCashValueBtnClicked(cashItem: DonationCashItems) {
        editCashTemp = cashItem
        val textPlaceholder = getString(R.string.popup_del_cash_placeholder)

        popUpDeleteBtnConfirm.setOnClickListener {
            viewModel.deleteCash(editCashTemp)
            popUpDelete.dismiss()
        }

        popUpDelText.text = format(textPlaceholder, cashItem.cashName, convertCurrency(cashItem.cashValue.roundToInt()))
        popUpDelete.show()
    }

    override fun onDeleteGoodsBtnClicked(goodsItem: DonationGoodsItems) {
        editGoodsTemp = goodsItem
        val textPlaceholder = getString(R.string.popup_del_goods_placeholder)

        popUpDeleteBtnConfirm.setOnClickListener {
            viewModel.deleteGoods(editGoodsTemp)
            popUpDelete.dismiss()
        }

        popUpDelText.text = format(textPlaceholder, goodsItem.goodsName, goodsItem.goodsWeight)
        popUpDelete.show()
    }


}