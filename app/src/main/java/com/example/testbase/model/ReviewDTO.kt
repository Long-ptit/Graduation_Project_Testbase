package com.example.testbase.model

data class ReviewDTO(
    var listStaReview: List<StatisticReview>,
    var averageRate: Double,
    var totalReview: Int
)
