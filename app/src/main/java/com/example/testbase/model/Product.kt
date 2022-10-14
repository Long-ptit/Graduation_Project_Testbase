package com.example.testbase.model

import java.io.Serializable

class Product(
    val id: Int,
    val name: String,
    val description: String,
    val quantity: Int,
    val price: Int,
    val seller: Seller,
    val productCategory: Category
) : Serializable
