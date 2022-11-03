package com.example.testbase.model

import java.io.Serializable

class Product(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val quantity: Int = 0,
    val price: Int = 0,
    val seller: Seller = Seller(),
    val productCategory: Category = Category()
) : Serializable
