package com.frhanklindevs.bantukuy.donor.ui.fragments.dashboard.widgets.donasi

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
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.FragmentBantuDonasiWidgetBinding
import com.frhanklindevs.bantukuy.donor.data.box.DonationCashItems
import com.frhanklindevs.bantukuy.donor.ui.bottomnav.BottomNavListener
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity
import com.frhanklindevs.bantukuy.utils.PopUpCreator
import com.frhanklindevs.bantukuy.utils.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.popup_add.*

class BantuDonasiWidget : Fragment() {

    private var _binding : FragmentBantuDonasiWidgetBinding? = null
    private val binding get() = _binding!!

    private var userId: Int = 0
    private lateinit var viewModel: BantuDonasiWidgetViewModel

    private lateinit var popUpAddEdit: Dialog
    private lateinit var popUpImg: ImageView
    private lateinit var popUpText: TextView
    private lateinit var popUpSpinner: Spinner
    private lateinit var goodsArrayAdapter: ArrayAdapter<CharSequence>
    private lateinit var popUpEditValue: TextInputEditText
    private lateinit var popUpAddEditBtnConfirm: AppCompatImageButton
    private lateinit var editCashTemp: DonationCashItems


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentBantuDonasiWidgetBinding.inflate(inflater, container, false)
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
            setAdapter()
            setViewModel()
            setViewBehaviors()
        }
    }

    private fun setAdapter() {
        goodsArrayAdapter =
            ArrayAdapter.createFromResource(
                requireContext(), R.array.spinner_goods_categories,
                android.R.layout.simple_spinner_item
            ).also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
    }

    private fun setView() {
        popUpAddEdit = PopUpCreator.createSmallAddPopUpDialog(requireContext())
        popUpImg = popUpAddEdit.findViewById(R.id.popup_img)
        popUpText = popUpAddEdit.findViewById(R.id.popup_text)
        popUpSpinner = popUpAddEdit.findViewById(R.id.popup_spinner)
        popUpEditValue = popUpAddEdit.findViewById(R.id.ed_cash_value)
        popUpAddEditBtnConfirm = popUpAddEdit.findViewById(R.id.btn_add_confirm)
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        viewModel = ViewModelProvider(this, factory)[BantuDonasiWidgetViewModel::class.java]

        viewModel.isDonateable.observe(viewLifecycleOwner, {
            binding.bantuDonasiWarning.visibility = if (it) View.GONE else View.VISIBLE
            binding.bantuDonasiContainer.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.currentDonationWeight.observe(viewLifecycleOwner, {
            binding.bantuDonasiOverviewNumber.text = it.toString()
        })

        viewModel.setUserId(userId)
    }

    private fun setViewBehaviors() {
        val bottomNavigationView = activity?.findViewById(R.id.bottom_nav_main) as BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(BottomNavListener.getBottomNavigationListenerFragment(this))

        binding.bantuDonasiBase.setOnClickListener {
            viewModel.setUserId(userId)
        }

        binding.bantuDonasiEdit.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.nav_tab_donation_box
        }
        binding.btnReload.setOnClickListener {
            viewModel.setUserId(userId)
        }

        binding.bantuDonasiAdd.setOnClickListener {
            // TODO : Show Popup (Lists: Donation item categories, TextField (weight), Add Button)
            popUpSpinner.adapter = goodsArrayAdapter
            popUpEditValue.setText("")
            popUpImg.setImageDrawable(context?.let { ctx ->
                AppCompatResources.getDrawable(ctx, R.drawable.ic_box_open_solid)
            })
            popUpText.text = getString(R.string.add_goods)
            popUpEditValue.setHint(getString(R.string.weight))
            popUpAddEdit.show()

        }

        popUpAddEditBtnConfirm.setOnClickListener(addGoodsAction)

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


}