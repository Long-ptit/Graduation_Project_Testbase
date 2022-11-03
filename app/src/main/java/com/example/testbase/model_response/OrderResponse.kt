package com.example.testbase.model_response

import com.example.testbase.model.CartItem
import com.example.testbase.model.Order

data class OrderResponse(
    var data: Order,
    var msg: String
)
