package com.example.myapplication.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAccountBinding
import com.example.myapplication.fragment.AccountTermFragment

class AccountActivity : AppCompatActivity() {
    private lateinit var accountBinding:ActivityAccountBinding

    private lateinit var fragmentManager:FragmentManager
    private lateinit var transaction: FragmentTransaction
    private val termFragment = AccountTermFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        accountBinding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(accountBinding.root)

        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.account_frameLayout, termFragment).commitAllowingStateLoss()

        this.onBackPressedDispatcher.addCallback(this, callBack)
    }

    private val callBack = object:OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            val builder = AlertDialog.Builder(this@AccountActivity)
            builder.setTitle("회원가입 종료").setMessage("지금까지 작성하신 내용이 모두 삭제됩니다.\n종료하시겠습니까?")
            builder.setPositiveButton("예"){
                _, _ -> finish()
            }
            builder.setNegativeButton("아니오"){
                dialog, _ -> dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}