package com.example.testbase.model_response

import com.example.testbase.model.CartItem

data class CartItemResponse(
    var data: CartItem,
    var msg: String
)
