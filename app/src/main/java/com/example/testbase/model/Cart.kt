package com.example.testbase.model

import java.io.Serializable

data class Cart(
    var id: Int,
    var createAt: Long,
    var totalPrice: Long,
    var totalQuantity: Long,
    var user: User
): Serializable
