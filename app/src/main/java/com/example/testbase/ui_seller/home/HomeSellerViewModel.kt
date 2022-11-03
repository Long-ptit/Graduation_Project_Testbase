package com.example.testbase.ui_seller.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testbase.model.Product
import com.example.testbase.base.BaseViewModel
import com.example.testbase.network.Api
import com.example.testbase.util.FirebaseUtil
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