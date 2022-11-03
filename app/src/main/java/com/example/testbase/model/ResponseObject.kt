package com.example.testbase.model

import java.io.Serializable

data class ResponseObject(
    var data: Any,
    val msg: String
) : Serializable