package com.example.testbase.base

import java.io.Serializable

data class BaseResponse(
    var data: Any,
    var msg: String,
) : Serializable