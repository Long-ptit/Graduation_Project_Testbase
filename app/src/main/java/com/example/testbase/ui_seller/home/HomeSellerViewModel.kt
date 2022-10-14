package com.example.testbase.ui_seller.home

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testbase.R
import com.example.testbase.base.BaseResponse
import com.example.testbase.model.Product
import com.example.testbase.model.User
import com.example.testbase.view_model.BaseViewModel
import com.example.testbase.network.Api
import com.example.testbase.util.Const
import com.example.testbase.util.FirebaseUtil
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeSellerViewModel @Inject constructor(val api: Api) : BaseViewModel() {
    val stateListProduct = MutableLiveData<List<Product>>()

    fun getListProductBySeller() {
        api.getListProductBySeller(FirebaseUtil.getUid()).enqueue(object : Callback<List<Product>?> {
            override fun onResponse(
                call: Call<List<Product>?>,
                response: Response<List<Product>?>
            ) {
                stateListProduct.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Product>?>, t: Throwable) {
                Log.d("ptit", "onFailure: " + t.message.toString())
            }
        })
    }

}