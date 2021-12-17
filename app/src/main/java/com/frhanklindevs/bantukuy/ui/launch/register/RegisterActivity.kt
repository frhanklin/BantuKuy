package com.frhanklindevs.bantukuy.ui.launch.register

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.ActivityRegisterBinding
import com.frhanklindevs.bantukuy.ui.launch.login.LoginActivity
import com.frhanklindevs.bantukuy.utils.PopUpCreator
import com.frhanklindevs.bantukuy.utils.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private var _binding : ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RegisterViewModel

    private lateinit var popup: Dialog
    private lateinit var popupImg: ImageView
    private lateinit var popupText: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setViews()
        setViewModel()
        setFunctionality()
    }

    private fun setViews() {
        popup = PopUpCreator.createSmallPopUpDialog(this)
        popupImg = popup.findViewById(R.id.popup_img)
        popupText = popup.findViewById(R.id.popup_text)
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]

        viewModel.warningImg.observe(this, {
            popupImg.setImageResource(it)
        })
        viewModel.warningText.observe(this, {
            popupText.text = it
        })
    }

    private fun setFunctionality() {
        binding.registerBtnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        binding.registerBtnRegister.setOnClickListener {
            val fullName = binding.registerEdName.text.toString()
            val email = binding.registerEdEmail.text.toString()
            val username = binding.registerEdUsername.text.toString()
            val password = binding.registerEdPassword.text.toString()
            val passwordVerify = binding.registerEdPasswordConfirm.text.toString()

            if (viewModel.isInsertable(fullName, email, username, password, passwordVerify)) {
                viewModel.insertUser(fullName, email, username, password)
                popup.show()
            } else {
                popup.show()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}