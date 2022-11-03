package com.example.testbase.model_response

import com.example.testbase.model.CartItem
import com.example.testbase.model.Order
import com.example.testbase.model.Review

data class ListReviewResponse(
    var data: List<Review>,
    var msg: String
)
