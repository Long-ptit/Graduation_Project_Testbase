package com.example.testbase.network

import com.example.testbase.base.BaseResponse
import com.example.testbase.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface Api {

    val path: String
        get() = "api/v1/user"

    @GET("api/v1/user/allUser")
    fun getAllFood(): Call<BaseResponse>

    @POST("api/v1/user/saveUser")
    fun saveUser(@Body user: User): Call<BaseResponse>

}