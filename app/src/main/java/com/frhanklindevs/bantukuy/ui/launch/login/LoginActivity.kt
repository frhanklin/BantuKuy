package com.frhanklindevs.bantukuy.ui.launch.login

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.frhanklindevs.bantukuy.R
import com.frhanklindevs.bantukuy.databinding.ActivityLoginBinding
import com.frhanklindevs.bantukuy.donor.ui.home.DonorHomeActivity
import com.frhanklindevs.bantukuy.ui.launch.register.RegisterActivity
import com.frhanklindevs.bantukuy.utils.PopUpCreator
import com.frhanklindevs.bantukuy.utils.ViewModelFactory

class LoginActivity : AppCompatActivity() {
    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    private lateinit var popup: Dialog
    private lateinit var popupImg: ImageView
    private lateinit var popupText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
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
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        viewModel.warningText.observe(this, {
            popupText.text = it
        })
        viewModel.warningImg.observe(this, {
            popupImg.setImageResource(it)
        })
    }

    private fun setFunctionality() {
        binding.loginBtnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        binding.loginBtnLogin.setOnClickListener {
            val username = binding.loginEdUsername.text.toString()
            val password = binding.loginEdPassword.text.toString()

            if (viewModel.userSearchable(username, password)) {
                if (viewModel.verifyUser(username, password)) {
                    val intent = Intent(this, DonorHomeActivity::class.java)
                    intent.putExtra(DonorHomeActivity.EXTRA_USER_ID, viewModel.getUserId())
                    intent.putExtra(DonorHomeActivity.EXTRA_BOX_ID, viewModel.getBoxId())
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                } else {
                    popup.show()
                }
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