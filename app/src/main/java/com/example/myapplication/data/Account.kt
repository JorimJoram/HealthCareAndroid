package com.example.myapplication.data

import java.time.LocalDateTime

data class Account(
    val username:String,
    val password:String? = "",

    val name:String,
    val phone:String,

    val license:String,
    val birth:String,
    val gender:Int,

    val createdDate:String
)
