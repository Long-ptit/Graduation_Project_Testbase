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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSellerViewModel @Inject constructor(val api: Api, val apiFcm: ApiFcm) : BaseViewModel() {


    val stateOrderById = MutableLiveData<OrderResponse>()
    var mOrder = Order()
    val stateListOrderItem = MutableLiveData<ListOrderItemResponse>()


    fun getOrderById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = api.getOrderById(id)
            mOrder = data.data
            stateOrderById.postValue(data)
        }

    }

    fun getOrderItemByOrder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            stateListOrderItem.postValue(api.getOrderItemByOrder(id))
        }

    }

    fun changeStatusOrder(status: String, idOrder: Int, message: String) {
        FirebaseUtil.changeStatusOrder(idOrder, status) {
            FirebaseUtil.sendNotification(mOrder.cart.user.id, NotificationSend(message), apiFcm)
        }
    }


}