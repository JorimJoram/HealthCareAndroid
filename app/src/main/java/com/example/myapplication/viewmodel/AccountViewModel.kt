package com.example.myapplication.viewmodel

import com.example.myapplication.config.RetrofitInstance
import retrofit2.awaitResponse

class AccountViewModel {
    private val accountApi = RetrofitInstance.accountApi

    suspend fun getAccountByUsername(username:String) = accountApi.getAccountByUsername(username).awaitResponse().body()
    suspend fun checkAccountPhone(phone:String) = accountApi.checkAccountPhone(phone).awaitResponse().body()
}