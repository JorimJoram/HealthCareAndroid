package com.example.myapplication.fragment

import android.os.Bundle
import android.provider.ContactsContract.Data
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.Account
import com.example.myapplication.databinding.FragmentAccountProfileBinding
import com.example.myapplication.viewmodel.AccountViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class AccountProfileFragment : Fragment() {
    private lateinit var binding:FragmentAccountProfileBinding

    private val account = Account.getInstance()
    private val accountViewModel = AccountViewModel()
    private var isPasswordConfirm = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkUsername()
        checkPassword()

        val confirmPasswordButton = binding.accountProfileConfirmButton
        confirmPasswordButton.setOnClickListener(confirmPassword)

        val finButton = binding.accountProfileFinButton
        finButton.setOnClickListener(sendData)
    }

    private val sendData = OnClickListener {
        lifecycleScope.launch {
            val result = accountViewModel.createAccount(account)!!
            try{
                if(result.gender != -1){
                    Toast.makeText(requireContext(), "회원가입이 성공적으로 마무리되었습니다\n로그인을 해주세요", Toast.LENGTH_LONG).show()
                    activity?.finish()
                }
                Toast.makeText(requireContext(), "회원가입이 마무리되지 못했습니다\n다시 시도해주세요", Toast.LENGTH_LONG).show()
            }catch (e:SocketTimeoutException){
                Toast.makeText(requireContext(), "인터넷 연결 시도 중입니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()
            }catch (e:Exception){
                Toast.makeText(requireContext(), "애러가 발생했습니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()
            }
        }
    }

    private val confirmPassword = OnClickListener{
        val confirm = binding.accountProfileConfirm.text.toString()
        if(confirm == account.password){
            isPasswordConfirm = true
            Toast.makeText(requireContext(), "비밀번호 확인", Toast.LENGTH_LONG).show()
        }else{
            isPasswordConfirm = false
            Toast.makeText(requireContext(), "비밀번호 다시 입력해주세요", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkUsername() {
        val usernameEdit = binding.accountProfileUsername
        usernameEdit.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                val username = (v as EditText).text.toString()
                if(checkUsernameRegex(username)){
                    checkUsernameDuplication(username)
                }else{
                    Toast.makeText(requireContext(), "아이디 형식을 확인해주세요", Toast.LENGTH_LONG).show()
                    account.username = ""
                }
            }
        }
    }

    private fun checkPassword(){
        val passwordEdit = binding.accountProfilePassword
        passwordEdit.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                val password = (v as EditText).text.toString()
                val passwordRegex = Regex("^[a-zA-Z0-9._@!-]{4,}\$")
                if(!passwordRegex.matches(password)){
                    Toast.makeText(requireContext(), "비밀번호를 다시 입력해주세요", Toast.LENGTH_LONG).show()
                    account.password=""
                }else{
                    account.password = password
                }
            }
        }
    }

    private fun checkUsernameDuplication(username: String) {
        var isDup = false
        lifecycleScope.launch {
            try{
                isDup = accountViewModel.checkAccountUsername(username)!!
                if(isDup){
                    account.username = username
                    Toast.makeText(requireContext(), "등록성공: ${account.username}", Toast.LENGTH_LONG).show()
                }else{
                    account.username = ""
                    Toast.makeText(requireContext(), "이미 등록된 아이디입니다: ${account.username}", Toast.LENGTH_LONG).show()
                }
            }catch (e:SocketTimeoutException){
                Toast.makeText(requireContext(), "인터넷 연결 시도 중입니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()
            }catch (e:Exception){
                Toast.makeText(requireContext(), "무슨 애러인지 찾아보세요", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkUsernameRegex(username: String): Boolean {
        val usernameRegex = Regex("^[a-zA-Z0-9]{4,}\$")
        return usernameRegex.matches(username)
    }
}
