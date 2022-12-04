package com.acom.service.user.payment


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PaymentNetwork {
    private var retrofit: Retrofit? = null
    fun payment(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.stripe.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}