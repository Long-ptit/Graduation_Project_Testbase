package com.example.testbase.model

data class OrderItem(
    var id: Int = 0,
    var name: String = "",
    var price: Long = 0,
    var quantity: Int = 0,
    var idProduct: Int = 0,
    var order: Order = Order(),
)
