package com.example.testbase.model_response

import com.example.testbase.model.CartItem
import com.example.testbase.model.Order

data class ListOrderResponse(
    var data: List<Order>,
    var msg: String
)
