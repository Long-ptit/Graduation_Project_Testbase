package com.example.testbase.model

import java.io.Serializable

open class User(

    var id: String = "",
    var name: String = "",
    var address: String = "",
    var email: String = "",
    var phone: String = "",
    var userType: Int = 0,
    var path: String = "",

    ) : Serializable