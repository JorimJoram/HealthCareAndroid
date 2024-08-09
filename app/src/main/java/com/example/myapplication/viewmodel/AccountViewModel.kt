package com.example.myapplication.viewmodel

import com.example.myapplication.config.RetrofitInstance
import com.example.myapplication.data.Account
import retrofit2.awaitResponse

class AccountViewModel {
    private val accountApi = RetrofitInstance.accountApi

    suspend fun getAccountByUsername(username:String) = accountApi.getAccountByUsername(username).awaitResponse().body()
    suspend fun checkAccountPhone(phone:String) = accountApi.checkAccountPhone(phone).awaitResponse().body()
    suspend fun checkAccountUsername(username:String) = accountApi.checkAccountUsername(username).awaitResponse().body()

    suspend fun createAccount(account: Account) = accountApi.createAccount(account).awaitResponse().body()
}