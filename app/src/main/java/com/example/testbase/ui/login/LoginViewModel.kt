package com.example.testbase.ui.login

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testbase.R
import com.example.testbase.base.BaseResponse
import com.example.testbase.view_model.BaseViewModel
import com.example.testbase.network.Api
import com.example.testbase.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : BaseViewModel() {

    val test = MutableLiveData<Boolean>(false)
    lateinit var api: Api


    fun getDataFromApi() {
       // showLoading()
        Handler().postDelayed({
            hideLoading()
        }, 2000)
    }

    fun callApi() {

        api.getAllFood().enqueue(object : Callback<BaseResponse?> {
            override fun onResponse(call: Call<BaseResponse?>, response: Response<BaseResponse?>) {
                Log.d("ptit", "onResponse: ")
            }

            override fun onFailure(call: Call<BaseResponse?>, t: Throwable) {
                errorMessage.value = R.string.default_error
            }
        })
    }
}