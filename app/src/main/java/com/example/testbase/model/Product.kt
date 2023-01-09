package com.example.testbase.model

import com.example.testbase.util.LogUtil
import java.io.Serializable

data class Product(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val quantity: Int = 0,
    val price: Int = 0,
    val seller: Seller = Seller(),
    val productCategory: Category = Category(),
    val discountPoint: Int = 0,
    val discount: Boolean = false,
    val msgDiscount: String = "",
    //new attribute
    val specification: String = "",
    val yearPublish: String = "",
    val soldNumber: Int = 0,
    val numReview: Int = 0,
    val manufacturer: Manufacturer = Manufacturer()

) : Serializable {

    fun getPriceAfterDiscount(): Int {
        LogUtil.log(this.toString())
        if (!discount) {
            return price
        } else {
            LogUtil.log("go here" + 30000*90/100)
            return price - (price*discountPoint/100)
        }
    }
}
