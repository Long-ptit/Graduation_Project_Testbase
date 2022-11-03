package com.example.testbase.model_response

import com.example.testbase.model.CartItem
import com.example.testbase.model.Order
import com.example.testbase.model.ReviewDTO

data class StatisReviewResponse(
    var data: ReviewDTO,
    var msg: String
)
