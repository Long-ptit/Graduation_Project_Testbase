package com.example.testbase.model

data class Order(
    var id: Int? = null,
    var status: String = "",
    var createAt: Long = 0,
    var totalPrice: Long = 0,
    var totalQuantity: Int = 0,
    var description: String = "",
    var cart: Cart = Cart(),
    var shippingInformation: ShippingInformation = ShippingInformation(),
    var seller: Seller = Seller(),
    var isReviewed: Boolean = false
)
