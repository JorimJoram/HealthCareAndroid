package com.example.myapplication.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.viewmodel.AccountViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBinding:ActivityLoginBinding
    private val accountViewModel = AccountViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        getAccountTest()
    }

    private fun getAccountTest(){
        lifecycleScope.launch {
            val testResult = accountViewModel.getAccountByUsername("jorimjoram")
            if (testResult != null) {
                println("testResult\n$testResult")
                loginBinding.testTextView.text = "${testResult.name} & ${testResult.createdDate}"
            }
        }
    }
}