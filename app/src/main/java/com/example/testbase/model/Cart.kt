package com.example.testbase.model

import java.io.Serializable

data class Cart(
    var id: Int? = null,
    var createAt: Long = 0,
    var totalPrice: Long = 0,
    var totalQuantity: Long = 0,
    var user: User = User()
): Serializable
