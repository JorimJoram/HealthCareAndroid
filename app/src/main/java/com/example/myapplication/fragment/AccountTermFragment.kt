package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAccountTermBinding

class AccountTermFragment : Fragment() {
    private lateinit var binding: FragmentAccountTermBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_term, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bothCheck = binding.accountTermBothCheck
        val serviceCheck = binding.accountTermServiceCheck
        val privateCheck = binding.accountTermPrivateCheck

        bothCheck.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                serviceCheck.isChecked = true
                privateCheck.isChecked = true
            }else{
                serviceCheck.isChecked = false
                privateCheck.isChecked = false
            }
        }
        serviceCheck.setOnCheckedChangeListener { _, isChecked ->  checkBoxLogics(bothCheck, serviceCheck, privateCheck)}
        privateCheck.setOnCheckedChangeListener { _, isChecked ->  checkBoxLogics(bothCheck, serviceCheck, privateCheck)}

    }

    private fun checkBoxLogics(bothCheck:CheckBox, serviceCheck:CheckBox, privateCheck:CheckBox){
        bothCheck.isChecked = serviceCheck.isChecked && privateCheck.isChecked
    }
}