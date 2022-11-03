package com.example.testbase.model_response

import com.example.testbase.model.CartItem
import com.example.testbase.model.Order
import com.example.testbase.model.User
import java.io.Serializable

data class UserResponse(
    var data: User,
    var msg: String
)
