package com.example.testbase.ui_seller.profile_seller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.testbase.model.Order
import com.example.testbase.model_firebase.NotificationSend
import com.example.testbase.model_response.ListOrderItemResponse
import com.example.testbase.model_response.OrderResponse
import com.example.testbase.network.Api
import com.example.testbase.service.ApiFcm
import com.example.testbase.util.FirebaseUtil
import com.example.testbase.base.BaseViewModel
import com.example.testbase.model.Product
import com.example.testbase.model.Seller
import com.example.testbase.model_response.SellerResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSellerViewModel @Inject constructor(val api: Api, val apiFcm: ApiFcm) : BaseViewModel() {

    val listProductSeller = MutableLiveData<List<Product>>()
    val sellerInfor = MutableLiveData<SellerResponse>()
    fun getListProductSeller(id: String) {
        viewModelScope.launch {
            listProductSeller.postValue(api.getListProductBySeller(id))
        }
    }

    fun getInforSeller(id: String) {
        viewModelScope.launch {
            sellerInfor.postValue(api.getInforSeller(id))
        }
    }



}