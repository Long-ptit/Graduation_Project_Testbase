package com.example.testbase.ui_seller.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testbase.R
import com.example.testbase.base.BaseResponse
import com.example.testbase.model.User
import com.example.testbase.base.BaseViewModel
import com.example.testbase.model_response.SellerResponse
import com.example.testbase.model_response.UserResponse
import com.example.testbase.network.Api
import com.example.testbase.service.ApiFcm
import com.example.testbase.util.FirebaseUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(val api: Api, val apiFcm: ApiFcm) : BaseViewModel() {
    private val auth = Firebase.auth
    val stateLogin = MutableLiveData<String>()
    val stateUser = MutableLiveData<UserResponse>()
    val stateSeller = MutableLiveData<SellerResponse>()

    fun getUserInfo(id: String) {
        api.getUserInfor(id).enqueue(object : Callback<UserResponse?> {
            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                val user = response.body()?.data
                stateUser.postValue(response.body())
            }

            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                errorMessage.value = R.string.default_error
            }
        })

    }

    fun getSellerInfo(id: String) {
        api.getInforSeller(id).enqueue(object : Callback<SellerResponse?> {
            override fun onResponse(call: Call<SellerResponse?>, response: Response<SellerResponse?>) {
                val user = response.body()?.data
                stateSeller.postValue(response.body())
            }

            override fun onFailure(call: Call<SellerResponse?>, t: Throwable) {
                errorMessage.value = R.string.default_error
            }
        })

    }

    fun loggout() {
        FirebaseUtil.deleteToken()
        auth.signOut()
    }
}