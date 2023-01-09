package com.example.testbase.model

data class ShippingInformation(
    var id: Int? = null,
    var name: String = "",
    var phone: String = "",
    var address: String = "",
    var user: User = User(),
    var isDefault: Boolean = false,
    var isSelected: Boolean = false
)
