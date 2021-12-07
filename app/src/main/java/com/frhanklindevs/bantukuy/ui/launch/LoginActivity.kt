package com.frhanklindevs.bantukuy.ui.launch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frhanklindevs.bantukuy.databinding.ActivityLoginBinding
import com.frhanklindevs.bantukuy.ui.donor.home.DonorHomeActivity

class LoginActivity : AppCompatActivity() {
    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setFunctionality()

    }

    private fun setFunctionality() {
        binding.loginBtnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        binding.loginBtnLogin.setOnClickListener {
            val intent = Intent(this, DonorHomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
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