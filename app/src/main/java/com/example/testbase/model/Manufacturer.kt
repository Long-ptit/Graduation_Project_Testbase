package com.example.testbase.model

import com.example.testbase.util.LogUtil
import java.io.Serializable

data class Manufacturer(
    val id: Int = 0,
    val name: String = "",
    val address: String = "",
    val phone: String = "",
    val yearPublish: String = "",
    val email: String = "",
    val taxNumber: String = ""

) : Serializable
