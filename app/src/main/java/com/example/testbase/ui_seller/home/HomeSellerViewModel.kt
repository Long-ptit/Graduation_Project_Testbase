package com.example.testbase.ui_seller.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model.Product
import com.example.testbase.base.BaseViewModel
import com.example.testbase.network.Api
import com.example.testbase.util.FirebaseUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeSellerViewModel @Inject constructor(val api: Api) : BaseViewModel() {
    val stateListProduct = MutableLiveData<List<Product>>()

    fun getListProductBySeller() {
        viewModelScope.launch {
            stateListProduct.postValue(api.getListProductBySeller(FirebaseUtil.getUid()))
        }
    }

}