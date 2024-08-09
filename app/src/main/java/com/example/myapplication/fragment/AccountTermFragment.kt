package com.example.myapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
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

        val nextButton = binding.accountTermNextButton
        nextButton.isEnabled = false
        nextButton.setOnClickListener(nextFragment)

        bothCheck.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                serviceCheck.isChecked = true
                privateCheck.isChecked = true
                nextButton.isEnabled = true
            }else{
                serviceCheck.isChecked = false
                privateCheck.isChecked = false
                nextButton.isEnabled = false
            }
        }
        serviceCheck.setOnCheckedChangeListener { _, _ ->  checkBoxLogics(bothCheck, serviceCheck, privateCheck, nextButton)}
        privateCheck.setOnCheckedChangeListener { _, _ ->  checkBoxLogics(bothCheck, serviceCheck, privateCheck, nextButton)}
    }

    private val nextFragment = OnClickListener{
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        val accountPersonalFragment = AccountPersonalFragment()
        transaction!!.replace(R.id.account_frameLayout, accountPersonalFragment)
        transaction.commitAllowingStateLoss()
    }

    private fun checkBoxLogics(bothCheck:CheckBox, serviceCheck:CheckBox, privateCheck:CheckBox, nextButton: Button){
        bothCheck.isChecked = serviceCheck.isChecked && privateCheck.isChecked
        nextButton.isEnabled = serviceCheck.isChecked && privateCheck.isChecked
    }
}