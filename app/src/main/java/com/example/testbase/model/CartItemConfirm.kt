package com.example.testbase.model

import java.io.Serializable

data class CartItemConfirm(
    var seller: Seller = Seller(),
    var listCartItem: ArrayList<CartItem> = arrayListOf()

)
