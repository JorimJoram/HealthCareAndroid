package com.example.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAccountBinding
import com.example.myapplication.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var startBinding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startBinding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(startBinding.root)
    }

    override fun onStart() {
        super.onStart()

        startBinding.startLoginButton.setOnClickListener(toLoginAct)
        startBinding.startAccountButton.setOnClickListener(toAccountAct)
    }

    private val toLoginAct = OnClickListener{
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private val toAccountAct = OnClickListener{
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
    }
}