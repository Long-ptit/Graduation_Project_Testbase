package com.acom.service.user.payment

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface IPaymentService {
    @POST("/v1/customers")
    fun createCustomerPayment(
        @Header("Authorization") author: String
    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("v1/ephemeral_keys")
    fun getEmPriceKey(
        @Header("Authorization") author: String,
        @Field("customer") customer_id: String,
        @Header("Stripe-Version") ver: String
    ): Call<JsonObject>

    @FormUrlEncoded
    @POST("v1/payment_intents")
    fun createPayment(
        @Header("Authorization") author: String?,
        @Field("customer") customer_id: String?,
        @Field("amount") amount: String?,
        @Field("currency") currency: String?,
        @Field("automatic_payment_methods[enabled]") state: String?
    ): Call<JsonObject>
}