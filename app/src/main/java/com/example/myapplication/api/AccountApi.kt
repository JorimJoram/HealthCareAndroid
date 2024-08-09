package com.example.myapplication.api

import com.example.myapplication.data.Account
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AccountApi {
    @GET("/account/get")
    fun getAccountByUsername(@Query("username")username:String): Call<Account>
    @GET("/account/check/dup/phone")
    fun checkAccountPhone(@Query("phone")phone: String): Call<Boolean>
}