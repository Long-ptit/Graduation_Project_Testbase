package com.example.testbase.model_response

import com.example.testbase.model.CartItem
import com.example.testbase.model.Order
import com.example.testbase.model.OrderItem

data class ListOrderItemResponse(
    var data: List<OrderItem>,
    var msg: String
)
