package com.example.testbase.model

import java.io.Serializable

data class Review(
    var id: Int? = null,
    var numStars: Int,
    var content: String,
    var createAt: Long = 0,
    var user: User,
    var orderItem: OrderItem
): Serializable
