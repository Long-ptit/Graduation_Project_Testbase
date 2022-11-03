package com.example.testbase.model

data class OrderItem(
    var id: Int,
    var name: String,
    var price: Long,
    var quantity: Int,
    var idProduct: Int,
    var order: Order,
)
