package com.example.myapplication.data

import java.time.LocalDateTime

data class Account(
    val username:String,
    val password:String? = "",

    var name:String,
    var phone:String,

    var license:String,
    var birth:String,
    var gender:Int,

    val createdDate:String? = LocalDateTime.now().toString()
){
    companion object{
        private var instance:Account? = null
        fun getInstance(): Account{
            if(instance == null){
                instance = Account(
                    username = "",
                    password = "",
                    name = "",
                    phone = "",
                    license = "",
                    birth = "",
                    gender = -1
                )
            }
            return instance!!
        }
    }
}
