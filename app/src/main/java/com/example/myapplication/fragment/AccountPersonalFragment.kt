package com.example.myapplication.fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.data.Account
import com.example.myapplication.databinding.FragmentAccountPersonalBinding
import com.example.myapplication.viewmodel.AccountViewModel
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException


class AccountPersonalFragment : Fragment() {
    private lateinit var binding: FragmentAccountPersonalBinding
    private val account = Account.getInstance()
    private val accountViewModel = AccountViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_personal, container, false)
        return binding.root
    }

    /**
     * TODO("lifeCycle에 대한 연구는 이후에 진행하면서 수정하겠습니다")
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkName()
        checkNumber()
        checkPhone() //TODO("아직 버튼에 대한 구체적인 규약이 없으므로, 일단 사용 안하는 선에서 해결해봤습니다")
        checkLicense()

        val nextButton = binding.accountPersonalNextButton
        nextButton.setOnClickListener(toNext)
    }

    private val toNext = OnClickListener{
        val licenseEdit = binding.accountPersonalLicense
        account.license = licenseEdit.text.toString()

        licenseEdit.clearFocus()
        hideKeyBoard(licenseEdit)

        if(account.name!="" && account.phone!="" && account.birth != "" && account.gender != -1 && account.license != ""){
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            val accountProfileFragment = AccountProfileFragment()
            transaction!!.replace(R.id.account_frameLayout, accountProfileFragment)
            transaction.commitAllowingStateLoss()
        }else{
            Toast.makeText(requireContext(), "아직 입력하지 않은 내용이 있습니다", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkName(){
        val nameEdit = binding.accountPersonalName
        nameEdit.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                val name = (v as EditText).text.toString()
                val koreanRegex = Regex("^[가-힣]+\$")
                if(!koreanRegex.matches(name)){
                    Toast.makeText(requireContext(), "이름을 다시 입력해주세요: $name", Toast.LENGTH_LONG).show()
                    account.name=""
                    //TODO("실패 메시지 어떻게 처리할 것인지에 대한 논의 필요")
                }else{
                    account.name = name
                }
            }
        }
    }

    private fun checkNumber(){
        val personalNumber = binding.accountPersonalNumber
        personalNumber.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                val number = (v as EditText).text.toString()
                val numberRegex = Regex("^\\d{7}\$")
                if(!numberRegex.matches(number)){
                    Toast.makeText(requireContext(), "주민등록번호를 다시 입력해주세요: $number", Toast.LENGTH_LONG).show()
                    //TODO("실패 메시지 어떻게 처리할 것인지에 대한 논의 필요")
                }else{
                    account.birth = number.substring(0,6)
                    account.gender = number.substring(6,7).toInt()
                    Toast.makeText(requireContext(), "${account.birth} - ${account.gender}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkPhone(){
        val personalPhone = binding.accountPersonalPhone
        personalPhone.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                val phone = binding.accountPersonalPhone.text.toString()
                if(checkPhoneRegex(phone)){
                    checkPhoneDuplication(phone)
                }else{
                    account.phone = ""
                    Toast.makeText(requireContext(), "휴대폰 번호를 다시 입력해주세요: $phone", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkLicense(){
        val personalLicense = binding.accountPersonalLicense
        personalLicense.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                val license = (v as EditText).text.toString()
                val licenseRegex = Regex("^\\d{1,7}\$")
                if(!licenseRegex.matches(license)){
                    Toast.makeText(requireContext(), "라이센스를 다시 입력해주세요: $license", Toast.LENGTH_LONG).show()
                    account.license = ""
                    //TODO("실패 메시지 어떻게 처리할 것인지에 대한 논의 필요")
                }else{
                    account.license = license.substring(0,6)
                    Toast.makeText(requireContext(), license, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkPhoneRegex(phone: String): Boolean {
        val phoneRegex = Regex("^\\d{11}\$") //3-4-4 -> 11자리 인지 확인
        return phoneRegex.matches(phone)
    }

    private fun checkPhoneDuplication(phone:String): Boolean {
        var isDup = false //false는 이미 가입되어있다는 의미
        lifecycleScope.launch {
            try{
                isDup = accountViewModel.checkAccountPhone(phone)!!
                if(isDup){
                    account.phone = phone
                    Toast.makeText(requireContext(), "등록성공: ${account.phone}", Toast.LENGTH_LONG).show()
                }else{
                    account.phone = ""
                    Toast.makeText(requireContext(), "이미 등록된 번호입니다: ${account.phone}", Toast.LENGTH_LONG).show()
                }
            }catch (e:SocketTimeoutException){
                Toast.makeText(requireContext(), "인터넷 연결 시도 중입니다. 다시 시도해주세요", Toast.LENGTH_LONG).show()
            }catch (e:Exception){
                Toast.makeText(requireContext(), "무슨 애러인지 찾아보세요", Toast.LENGTH_LONG).show()
            }
        }
        return isDup
    }

    private fun hideKeyBoard(licenseEdit: EditText) {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(licenseEdit.windowToken, 0)
    }
}