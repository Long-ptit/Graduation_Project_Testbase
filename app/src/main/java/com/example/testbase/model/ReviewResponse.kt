package com.example.testbase.model

import java.io.Serializable

data class ReviewResponse(
    var data: String,
    val msg: String
) : Serializable