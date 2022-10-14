package com.example.testbase.service

import com.example.testbase.model.SendNotiFcmRequest
import com.example.testbase.model_response.SendNotifiFcmResponse
import com.example.testbase.util.Const
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiFcm {
    @Headers(
        "Content-Type:application/json",
        "Authorization:key=${Const.SERVER_KEY}"
    )
    @POST("fcm/send")
    fun sendNotification(@Body request: SendNotiFcmRequest): Call<SendNotifiFcmResponse>
}