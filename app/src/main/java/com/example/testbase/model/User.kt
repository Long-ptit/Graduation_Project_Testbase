package com.example.testbase.model

import java.io.Serializable

data class User  (

     val id: String,
     val name: String,
     val address: String,
     val email: String,
     val phone: String,
     val uid: String

) : Serializable