package com.example.testbase.model

data class CartItem(
    var id: Int,
    var createAt: Long,
    var quantity: Long,
    var cart: Cart,
    var product: Product
)
