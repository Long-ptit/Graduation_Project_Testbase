package com.example.testbase.model_response

import com.example.testbase.model.CartItem
import com.example.testbase.model.Order
import com.example.testbase.model.Seller
import com.example.testbase.model.User
import java.io.Serializable

data class SellerResponse(
    var data: Seller,
    var msg: String
)
