package com.frhanklindevs.bantukuy.ui.launch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frhanklindevs.bantukuy.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}