package com.example.testbase.model

import java.io.Serializable

data class Seller(
    var shopName: String? = null
) : Serializable, User()
