package com.example.testbase.base

import java.io.Serializable

data class BaseResponse (
    var data: Object,
    var msg : String,
        ) : Serializable